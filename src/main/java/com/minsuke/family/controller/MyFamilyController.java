package com.minsuke.family.controller;

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
import com.minsuke.family.dto.ChildForm;
import com.minsuke.family.dto.HouseholdForm;
import com.minsuke.family.dto.ParentForm;
import com.minsuke.family.service.FamilyService;

import jakarta.validation.Valid;

@Controller
public class MyFamilyController {

    private final FamilyService familyService;

    public MyFamilyController(FamilyService familyService) {
        this.familyService = familyService;
    }

    @GetMapping("/my-family")
    public String myFamily(@AuthenticationPrincipal MinsukeUserDetails user, Model model) {
        model.addAttribute("household", familyService.getMyHousehold(user));
        return "family/my-family";
    }

    @GetMapping("/my-family/edit")
    public String editHouseholdForm(@AuthenticationPrincipal MinsukeUserDetails user, Model model) {
        model.addAttribute("householdForm", familyService.toHouseholdForm(user));
        return "family/household-edit";
    }

    @PostMapping("/my-family/edit")
    public String editHousehold(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @Valid @ModelAttribute("householdForm") HouseholdForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "family/household-edit";
        }
        familyService.updateMyHousehold(user, form);
        return "redirect:/my-family";
    }

    @GetMapping("/my-family/parents/new")
    public String newParentForm(Model model) {
        model.addAttribute("parentForm", new ParentForm());
        model.addAttribute("isEdit", false);
        return "family/parent-form";
    }

    @PostMapping("/my-family/parents/new")
    public String createParent(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @Valid @ModelAttribute("parentForm") ParentForm form,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "family/parent-form";
        }
        familyService.createParent(user, form);
        return "redirect:/my-family";
    }

    @GetMapping("/my-family/parents/{id}/edit")
    public String editParentForm(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @PathVariable Long id,
            Model model) {
        model.addAttribute("parentForm", familyService.toParentForm(user, id));
        model.addAttribute("parentId", id);
        model.addAttribute("isEdit", true);
        return "family/parent-form";
    }

    @PostMapping("/my-family/parents/{id}/edit")
    public String updateParent(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @PathVariable Long id,
            @Valid @ModelAttribute("parentForm") ParentForm form,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("parentId", id);
            model.addAttribute("isEdit", true);
            return "family/parent-form";
        }
        familyService.updateParent(user, id, form);
        return "redirect:/my-family";
    }

    @PostMapping("/my-family/parents/{id}/delete")
    public String deleteParent(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @PathVariable Long id) {
        familyService.deleteParent(user, id);
        return "redirect:/my-family";
    }

    @GetMapping("/my-family/children/new")
    public String newChildForm(Model model) {
        model.addAttribute("childForm", new ChildForm());
        model.addAttribute("isEdit", false);
        return "family/child-form";
    }

    @PostMapping("/my-family/children/new")
    public String createChild(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @Valid @ModelAttribute("childForm") ChildForm form,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "family/child-form";
        }
        familyService.createChild(user, form);
        return "redirect:/my-family";
    }

    @GetMapping("/my-family/children/{id}/edit")
    public String editChildForm(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @PathVariable Long id,
            Model model) {
        model.addAttribute("childForm", familyService.toChildForm(user, id));
        model.addAttribute("childId", id);
        model.addAttribute("isEdit", true);
        return "family/child-form";
    }

    @PostMapping("/my-family/children/{id}/edit")
    public String updateChild(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @PathVariable Long id,
            @Valid @ModelAttribute("childForm") ChildForm form,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("childId", id);
            model.addAttribute("isEdit", true);
            return "family/child-form";
        }
        familyService.updateChild(user, id, form);
        return "redirect:/my-family";
    }

    @PostMapping("/my-family/children/{id}/delete")
    public String deleteChild(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @PathVariable Long id) {
        familyService.deleteChild(user, id);
        return "redirect:/my-family";
    }
}
