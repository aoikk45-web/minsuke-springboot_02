package com.minsuke.event.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseholdParticipationRateDTO {

    private Long householdId;
    private String householdName;
    private int attendedCount;
    private int totalEvents;
    private int ratePercent;
}
