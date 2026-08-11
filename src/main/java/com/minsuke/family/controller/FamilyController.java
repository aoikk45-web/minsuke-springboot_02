package com.minsuke.family.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.minsuke.auth.security.MinsukeUserDetails;
import com.minsuke.family.service.FamilyService;

@Controller
public class FamilyController {

    private final FamilyService familyService;

    public FamilyController(FamilyService familyService) {
        this.familyService = familyService;
    }

    @GetMapping("/families")
    public String list(Model model) {
        model.addAttribute("households", familyService.listHouseholds());
        return "family/list";
    }

    @GetMapping("/families/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("household", familyService.getHouseholdDetail(id));
        return "family/detail";
    }

    @PostMapping("/families/{id}/delete")
    public String delete(
            @PathVariable Long id,
            @AuthenticationPrincipal MinsukeUserDetails user,
            RedirectAttributes redirectAttributes) {
        try {
            familyService.deleteHousehold(user, id);
            redirectAttributes.addFlashAttribute("successMessage", "家族を削除しました。");
            return "redirect:/families";
        } catch (IllegalStateException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/families/" + id;
        }
    }
}
