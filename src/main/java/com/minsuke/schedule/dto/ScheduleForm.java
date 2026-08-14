package com.minsuke.schedule.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.minsuke.event.domain.ParticipationUnit;
import com.minsuke.schedule.domain.ScheduleType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleForm {

    @NotBlank(message = "タイトルを入力してください")
    @Size(max = 200, message = "タイトルは200文字以内で入力してください")
    private String title;

    @NotBlank(message = "説明を入力してください")
    private String description;

    @NotNull(message = "種別を選択してください")
    private ScheduleType scheduleType;

    /** WEEKLY: 1=月 … 7=日（複数可） */
    private List<Integer> daysOfWeek = new ArrayList<>();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate oneOffDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate validFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate validUntil;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime startTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime endTime;

    @Positive(message = "定員は1以上で入力してください")
    private Integer capacity;

    private Long instructorId;

    @NotNull(message = "参加登録単位を選択してください")
    private ParticipationUnit participationUnit;

    private boolean active = true;
}
