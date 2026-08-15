package com.minsuke.event.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.minsuke.event.domain.ParticipationUnit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDetailDTO {

    private Long id;
    private String title;
    private String description;
    private LocalDate eventDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer capacity;
    private Long instructorId;
    private String instructorName;
    private long registeredCount;
    private boolean full;
    private boolean unlimitedCapacity;
    private Long scheduleId;
    private String scheduleTitle;
    private ParticipationUnit participationUnit;
    private String participationUnitLabel;
    /** 同じスケジュールの今後イベントへ一括参加できる */
    private boolean seriesAttendanceAvailable;
    private List<ParticipantOptionDTO> participantOptions = new ArrayList<>();
    private List<RegisteredParticipantDTO> registeredParticipants = new ArrayList<>();

    @Getter
    @Setter
    public static class ParticipantOptionDTO {
        private String key;
        private ParticipantType type;
        private Long parentId;
        private Long childId;
        private Long householdId;
        private String name;
        private boolean registered;
        private boolean canRegister;
    }

    @Getter
    @Setter
    public static class RegisteredParticipantDTO {
        private String name;
        private String typeLabel;
        private String householdName;
    }

    public enum ParticipantType {
        PARENT,
        CHILD,
        HOUSEHOLD
    }
}
