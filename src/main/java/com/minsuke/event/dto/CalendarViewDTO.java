package com.minsuke.event.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarViewDTO {

    private int year;
    private int month;
    private String monthLabel;
    private int previousYear;
    private int previousMonth;
    private int nextYear;
    private int nextMonth;
    /** PARENT で household があるとき true。凡例・本日参加を出す */
    private boolean showHouseholdParticipation;
    private List<CalendarEventDTO> todayParticipations = new ArrayList<>();
    private List<CalendarWeekDTO> weeks = new ArrayList<>();

    @Getter
    @Setter
    public static class CalendarWeekDTO {
        private List<CalendarDayDTO> days = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class CalendarDayDTO {
        private LocalDate date;
        private int dayOfMonth;
        private boolean currentMonth;
        private boolean today;
        private List<CalendarEventDTO> events = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class CalendarEventDTO {
        private Long id;
        private String title;
        private LocalTime startTime;
        private boolean full;
        private boolean participating;
    }
}
