package com.minsuke.event.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.minsuke.auth.security.MinsukeUserDetails;
import com.minsuke.event.service.EventService;

@Controller
public class ParticipationController {

    private final EventService eventService;

    public ParticipationController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/my-participations")
    public String list(@AuthenticationPrincipal MinsukeUserDetails user, Model model) {
        model.addAttribute("participations", eventService.listMyParticipations(user));
        return "event/participations";
    }
}
