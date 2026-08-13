package com.minsuke.announcement.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.minsuke.announcement.dto.AnnouncementForm;
import com.minsuke.announcement.service.AnnouncementService;
import com.minsuke.auth.security.MinsukeUserDetails;

import jakarta.validation.Valid;

@Controller
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping("/announcements")
    public String list(@AuthenticationPrincipal MinsukeUserDetails user, Model model) {
        model.addAttribute("announcements", announcementService.list(user));
        return "announcement/list";
    }

    @GetMapping("/announcements/new")
    public String createForm(Model model) {
        model.addAttribute("announcementForm", new AnnouncementForm());
        model.addAttribute("isEdit", false);
        return "announcement/form";
    }

    @PostMapping("/announcements/new")
    public String create(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @Valid @ModelAttribute("announcementForm") AnnouncementForm form,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "announcement/form";
        }
        Long id = announcementService.create(user, form);
        return "redirect:/announcements/" + id;
    }

    @GetMapping("/announcements/{id}")
    public String detail(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @PathVariable Long id,
            Model model) {
        model.addAttribute("announcement", announcementService.getDetail(id, user));
        return "announcement/detail";
    }

    @GetMapping("/announcements/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("announcementForm", announcementService.toForm(id));
        model.addAttribute("announcementId", id);
        model.addAttribute("isEdit", true);
        return "announcement/form";
    }

    @PostMapping("/announcements/{id}/edit")
    public String update(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @PathVariable Long id,
            @Valid @ModelAttribute("announcementForm") AnnouncementForm form,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("announcementId", id);
            model.addAttribute("isEdit", true);
            return "announcement/form";
        }
        announcementService.update(user, id, form);
        return "redirect:/announcements/" + id;
    }

    @PostMapping("/announcements/{id}/delete")
    public String delete(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        announcementService.delete(user, id);
        redirectAttributes.addFlashAttribute("successMessage", "お知らせを削除しました。");
        return "redirect:/announcements";
    }
}
