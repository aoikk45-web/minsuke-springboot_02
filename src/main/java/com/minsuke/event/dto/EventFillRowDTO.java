package com.minsuke.event.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventFillRowDTO {

    private Long eventId;
    private String eventTitle;
    private LocalDate eventDate;
    private LocalTime startTime;
    private Integer capacity;
    private long registeredCount;
    private Long remaining;
    private Integer fillPercent;
    private String scheduleTitle;
}
