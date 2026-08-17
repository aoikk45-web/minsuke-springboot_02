package com.minsuke.event.controller;

import java.time.YearMonth;
import java.time.ZoneId;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.minsuke.auth.security.MinsukeUserDetails;
import com.minsuke.event.dto.ScheduleParticipationViewDTO;
import com.minsuke.event.service.AdminParticipationService;

@Controller
public class AdminParticipationController {

    private static final ZoneId ZONE = ZoneId.of("Asia/Tokyo");

    private final AdminParticipationService adminParticipationService;

    public AdminParticipationController(AdminParticipationService adminParticipationService) {
        this.adminParticipationService = adminParticipationService;
    }

    @GetMapping("/admin/participations")
    public String monthlyFills(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            Model model) {
        YearMonth target = resolveMonth(year, month);
        model.addAttribute("yearMonth", target);
        model.addAttribute("prevMonth", target.minusMonths(1));
        model.addAttribute("nextMonth", target.plusMonths(1));
        model.addAttribute("fills", adminParticipationService.listMonthlyEventFills(user, target));
        return "event/admin-participations";
    }

    @GetMapping("/schedules/{id}/participations")
    public String scheduleRates(
            @AuthenticationPrincipal MinsukeUserDetails user,
            @PathVariable Long id,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            Model model) {
        YearMonth filter = (year != null && month != null) ? YearMonth.of(year, month) : null;
        ScheduleParticipationViewDTO view =
                adminParticipationService.listScheduleHouseholdRates(user, id, filter);
        model.addAttribute("view", view);
        model.addAttribute("currentMonth", YearMonth.now(ZONE));
        return "schedule/participations";
    }

    private YearMonth resolveMonth(Integer year, Integer month) {
        YearMonth now = YearMonth.now(ZONE);
        if (year == null || month == null) {
            return now;
        }
        return YearMonth.of(year, month);
    }
}
