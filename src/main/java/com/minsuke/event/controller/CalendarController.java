package com.minsuke.event.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.minsuke.event.service.EventService;

@Controller
public class CalendarController {

    private final EventService eventService;

    public CalendarController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/calendar")
    public String calendar(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            Model model) {
        model.addAttribute("calendar", eventService.buildCalendarView(year, month));
        return "calendar";
    }
}
