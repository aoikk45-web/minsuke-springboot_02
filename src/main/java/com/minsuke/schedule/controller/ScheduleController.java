package com.minsuke.schedule.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.minsuke.auth.security.MinsukeUserDetails;
import com.minsuke.event.domain.ParticipationUnit;
import com.minsuke.instructor.service.InstructorService;
import com.minsuke.schedule.domain.ScheduleType;
import com.minsuke.schedule.dto.ScheduleForm;
import com.minsuke.schedule.dto.ScheduleGenerateResultDTO;
import com.minsuke.schedule.service.ScheduleService;

import jakarta.validation.Valid;

@Controller
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final InstructorService instructorService;

    public ScheduleController(ScheduleService scheduleService, InstructorService instructorService) {
        this.scheduleService = scheduleService;
        this.instructorService = instructorService;
    }

    @GetMapping("/schedules")
    public String list(@AuthenticationPrincipal MinsukeUserDetails user, Model model) {
        model.addAttribute("schedules", scheduleService.list(user));
        return "schedule/list";
    }

    @GetMapping("/schedules/new")
    public String createForm(Model model) {
        prepareFormModel(model, new ScheduleForm(), null, false);
        return "schedule/form";
    }

    @PostMapping("/schedules/new")
    public String create(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @Valid @ModelAttribute("scheduleForm") ScheduleForm form,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            prepareFormModel(model, form, null, false);
            return "schedule/form";
        }
        try {
            Long id = scheduleService.create(user, form);
            return "redirect:/schedules/" + id;
        } catch (IllegalArgumentException ex) {
            bindingResult.reject("scheduleForm", ex.getMessage());
            prepareFormModel(model, form, null, false);
            return "schedule/form";
        }
    }

    @GetMapping("/schedules/{id}")
    public String detail(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @PathVariable Long id,
            Model model) {
        model.addAttribute("schedule", scheduleService.getDetail(id, user));
        model.addAttribute("generateWeeks", ScheduleService.DEFAULT_GENERATE_WEEKS);
        return "schedule/detail";
    }

    @GetMapping("/schedules/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        prepareFormModel(model, scheduleService.toForm(id), id, true);
        return "schedule/form";
    }

    @PostMapping("/schedules/{id}/edit")
    public String update(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @PathVariable Long id,
            @Valid @ModelAttribute("scheduleForm") ScheduleForm form,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            prepareFormModel(model, form, id, true);
            return "schedule/form";
        }
        try {
            scheduleService.update(user, id, form);
            return "redirect:/schedules/" + id;
        } catch (IllegalArgumentException ex) {
            bindingResult.reject("scheduleForm", ex.getMessage());
            prepareFormModel(model, form, id, true);
            return "schedule/form";
        }
    }

    @PostMapping("/schedules/{id}/delete")
    public String delete(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        scheduleService.delete(user, id);
        redirectAttributes.addFlashAttribute("successMessage", "スケジュールを削除しました。");
        return "redirect:/schedules";
    }

    @PostMapping("/schedules/{id}/generate")
    public String generate(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @PathVariable Long id,
            @RequestParam(required = false) Integer weeks,
            RedirectAttributes redirectAttributes) {
        try {
            ScheduleGenerateResultDTO result = scheduleService.generateEvents(user, id, weeks);
            redirectAttributes.addFlashAttribute("successMessage",
                    "イベントを " + result.getCreatedCount() + " 件作成しました（"
                            + result.getSkippedCount() + " 件スキップ）。");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/schedules/" + id;
    }

    private void prepareFormModel(Model model, ScheduleForm form, Long scheduleId, boolean isEdit) {
        model.addAttribute("scheduleForm", form);
        model.addAttribute("scheduleId", scheduleId);
        model.addAttribute("isEdit", isEdit);
        model.addAttribute("scheduleTypes", ScheduleType.values());
        model.addAttribute("participationUnits", ParticipationUnit.values());
        model.addAttribute("instructors", instructorService.listActiveInstructors());
    }
}
