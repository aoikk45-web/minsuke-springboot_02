package com.minsuke.event.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParticipationRowDTO {

    private Long eventId;
    private String eventTitle;
    private LocalDate eventDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String participantName;
    private String participantTypeLabel;
}
