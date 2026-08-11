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
import com.minsuke.event.dto.AttendanceForm;
import com.minsuke.event.dto.EventForm;
import com.minsuke.event.exception.EventCapacityFullException;
import com.minsuke.event.service.EventService;

import jakarta.validation.Valid;

@Controller
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events/new")
    public String createForm(Model model) {
        model.addAttribute("eventForm", new EventForm());
        return "event/create";
    }

    @PostMapping("/events")
    public String create(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @Valid @ModelAttribute("eventForm") EventForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "event/create";
        }
        try {
            Long eventId = eventService.createEvent(user, form);
            return "redirect:/events/" + eventId;
        } catch (IllegalArgumentException ex) {
            bindingResult.reject("eventForm", ex.getMessage());
            return "event/create";
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
            if ("cancel".equals(form.getAction())) {
                if ("PARENT".equals(form.getParticipantType())) {
                    eventService.cancelParent(user, id, form.getParentId());
                } else {
                    eventService.cancelChild(user, id, form.getChildId());
                }
                redirectAttributes.addFlashAttribute("successMessage", "参加をキャンセルしました。");
            } else {
                if ("PARENT".equals(form.getParticipantType())) {
                    eventService.registerParent(user, id, form.getParentId());
                } else {
                    eventService.registerChild(user, id, form.getChildId());
                }
                redirectAttributes.addFlashAttribute("successMessage", "参加登録しました。");
            }
        } catch (EventCapacityFullException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/events/" + id;
    }
}
