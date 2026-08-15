package com.minsuke.event.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.minsuke.auth.security.MinsukeUserDetails;
import com.minsuke.event.domain.ParticipationUnit;
import com.minsuke.event.dto.AttendanceForm;
import com.minsuke.event.dto.EventForm;
import com.minsuke.event.dto.SeriesAttendResultDTO;
import com.minsuke.event.exception.EventCapacityFullException;
import com.minsuke.event.service.EventService;
import com.minsuke.instructor.service.InstructorService;

import jakarta.validation.Valid;

@Controller
public class EventController {

    private final EventService eventService;
    private final InstructorService instructorService;

    public EventController(EventService eventService, InstructorService instructorService) {
        this.eventService = eventService;
        this.instructorService = instructorService;
    }

    @GetMapping("/events/new")
    public String createForm(Model model) {
        prepareEventForm(model, new EventForm(), null);
        return "event/create";
    }

    @PostMapping("/events")
    public String create(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @Valid @ModelAttribute("eventForm") EventForm form,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            prepareEventForm(model, form, null);
            return "event/create";
        }
        try {
            Long eventId = eventService.createEvent(user, form);
            return "redirect:/events/" + eventId;
        } catch (IllegalArgumentException ex) {
            bindingResult.reject("eventForm", ex.getMessage());
            prepareEventForm(model, form, null);
            return "event/create";
        }
    }

    @GetMapping("/events/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        prepareEventForm(model, eventService.toEventForm(id), id);
        return "event/edit";
    }

    @PostMapping("/events/{id}/edit")
    public String update(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @PathVariable Long id,
            @Valid @ModelAttribute("eventForm") EventForm form,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            prepareEventForm(model, form, id);
            return "event/edit";
        }
        try {
            eventService.updateEvent(user, id, form);
            return "redirect:/events/" + id;
        } catch (IllegalArgumentException ex) {
            bindingResult.reject("eventForm", ex.getMessage());
            prepareEventForm(model, form, id);
            return "event/edit";
        }
    }

    @GetMapping("/events/{id}")
    public String detail(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @PathVariable Long id,
            Model model) {
        model.addAttribute("event", eventService.getEventDetail(id, user));
        return "event/detail";
    }

    @PostMapping("/events/{id}/attend")
    public String attend(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @PathVariable Long id,
            @ModelAttribute AttendanceForm form,
            RedirectAttributes redirectAttributes) {
        try {
            boolean series = "series".equals(form.getScope());
            if ("cancel".equals(form.getAction())) {
                if (series) {
                    SeriesAttendResultDTO result = cancelSeries(user, id, form);
                    redirectAttributes.addFlashAttribute("successMessage", seriesMessage(true, result));
                } else {
                    if ("PARENT".equals(form.getParticipantType())) {
                        eventService.cancelParent(user, id, form.getParentId());
                    } else if ("HOUSEHOLD".equals(form.getParticipantType())) {
                        eventService.cancelHousehold(user, id);
                    } else {
                        eventService.cancelChild(user, id, form.getChildId());
                    }
                    redirectAttributes.addFlashAttribute("successMessage", "参加をキャンセルしました。");
                }
            } else if (series) {
                SeriesAttendResultDTO result = registerSeries(user, id, form);
                redirectAttributes.addFlashAttribute("successMessage", seriesMessage(false, result));
            } else {
                if ("PARENT".equals(form.getParticipantType())) {
                    eventService.registerParent(user, id, form.getParentId());
                } else if ("HOUSEHOLD".equals(form.getParticipantType())) {
                    eventService.registerHousehold(user, id);
                } else {
                    eventService.registerChild(user, id, form.getChildId());
                }
                redirectAttributes.addFlashAttribute("successMessage", "参加登録しました。");
            }
        } catch (EventCapacityFullException | IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/events/" + id;
    }

    private SeriesAttendResultDTO registerSeries(
            MinsukeUserDetails user, Long eventId, AttendanceForm form) {
        if ("PARENT".equals(form.getParticipantType())) {
            return eventService.registerParentSeries(user, eventId, form.getParentId());
        }
        if ("HOUSEHOLD".equals(form.getParticipantType())) {
            return eventService.registerHouseholdSeries(user, eventId);
        }
        return eventService.registerChildSeries(user, eventId, form.getChildId());
    }

    private SeriesAttendResultDTO cancelSeries(
            MinsukeUserDetails user, Long eventId, AttendanceForm form) {
        if ("PARENT".equals(form.getParticipantType())) {
            return eventService.cancelParentSeries(user, eventId, form.getParentId());
        }
        if ("HOUSEHOLD".equals(form.getParticipantType())) {
            return eventService.cancelHouseholdSeries(user, eventId);
        }
        return eventService.cancelChildSeries(user, eventId, form.getChildId());
    }

    private String seriesMessage(boolean cancel, SeriesAttendResultDTO result) {
        int applied = result.getAppliedCount();
        int skipped = result.getSkippedFullCount();
        if (applied == 0 && skipped == 0) {
            return cancel
                    ? "キャンセルできる今後の回はありませんでした。"
                    : "参加登録できる今後の回はありませんでした。";
        }
        StringBuilder message = new StringBuilder();
        if (applied > 0) {
            message.append(applied)
                    .append(cancel ? "件をキャンセルしました。" : "件に参加登録しました。");
        }
        if (skipped > 0) {
            if (!message.isEmpty()) {
                message.append(" ");
            }
            message.append("満員などで ").append(skipped).append("件スキップしました。");
        }
        return message.toString();
    }

    private void prepareEventForm(Model model, EventForm form, Long eventId) {
        model.addAttribute("eventForm", form);
        model.addAttribute("eventId", eventId);
        model.addAttribute("instructors", instructorService.listActiveInstructors());
        model.addAttribute("participationUnits", ParticipationUnit.values());
    }
}
