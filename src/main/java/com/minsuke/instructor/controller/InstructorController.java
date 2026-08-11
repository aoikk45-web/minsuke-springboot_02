package com.minsuke.instructor.controller;

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
import com.minsuke.instructor.dto.InstructorForm;
import com.minsuke.instructor.service.InstructorService;

import jakarta.validation.Valid;

@Controller
public class InstructorController {

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping("/instructors")
    public String list(@AuthenticationPrincipal MinsukeUserDetails user, Model model) {
        model.addAttribute("instructors", instructorService.listInstructors(user));
        return "instructor/list";
    }

    @GetMapping("/instructors/new")
    public String createForm(Model model) {
        model.addAttribute("instructorForm", new InstructorForm());
        model.addAttribute("isEdit", false);
        return "instructor/form";
    }

    @PostMapping("/instructors/new")
    public String create(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @Valid @ModelAttribute("instructorForm") InstructorForm form,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "instructor/form";
        }
        Long id = instructorService.create(user, form);
        return "redirect:/instructors/" + id;
    }

    @GetMapping("/instructors/{id}")
    public String detail(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @PathVariable Long id,
            Model model) {
        model.addAttribute("instructor", instructorService.getInstructor(id, user));
        return "instructor/detail";
    }

    @GetMapping("/instructors/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("instructorForm", instructorService.toForm(id));
        model.addAttribute("instructorId", id);
        model.addAttribute("isEdit", true);
        return "instructor/form";
    }

    @PostMapping("/instructors/{id}/edit")
    public String update(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @PathVariable Long id,
            @Valid @ModelAttribute("instructorForm") InstructorForm form,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("instructorId", id);
            model.addAttribute("isEdit", true);
            return "instructor/form";
        }
        instructorService.update(user, id, form);
        return "redirect:/instructors/" + id;
    }

    @PostMapping("/instructors/{id}/deactivate")
    public String deactivate(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        instructorService.deactivate(user, id);
        redirectAttributes.addFlashAttribute("successMessage", "講師を無効にしました。");
        return "redirect:/instructors/" + id;
    }

    @PostMapping("/instructors/{id}/delete")
    public String delete(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        instructorService.delete(user, id);
        redirectAttributes.addFlashAttribute("successMessage", "講師を削除しました。");
        return "redirect:/instructors";
    }
}
