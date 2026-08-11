package com.minsuke.event.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventForm {

    @NotBlank(message = "タイトルを入力してください")
    @Size(max = 200, message = "タイトルは200文字以内で入力してください")
    private String title;

    @NotBlank(message = "説明を入力してください")
    private String description;

    @NotNull(message = "開催日を入力してください")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate eventDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime startTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime endTime;

    @Positive(message = "定員は1以上で入力してください")
    private Integer capacity;
}
