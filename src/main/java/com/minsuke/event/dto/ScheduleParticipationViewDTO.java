package com.minsuke.event.dto;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleParticipationViewDTO {

    private Long scheduleId;
    private String scheduleTitle;
    private int totalEvents;
    private YearMonth filterMonth;
    private List<HouseholdParticipationRateDTO> households = new ArrayList<>();
}
