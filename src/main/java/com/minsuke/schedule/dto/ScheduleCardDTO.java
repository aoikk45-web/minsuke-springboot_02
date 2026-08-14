package com.minsuke.schedule.dto;

import com.minsuke.schedule.domain.ScheduleType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleCardDTO {

    private Long id;
    private String title;
    private ScheduleType scheduleType;
    private String scheduleTypeLabel;
    private boolean active;
    private long generatedEventCount;
}
