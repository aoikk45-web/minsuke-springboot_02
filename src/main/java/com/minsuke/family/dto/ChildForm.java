package com.minsuke.family.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChildForm {

    @NotBlank(message = "氏名を入力してください")
    @Size(max = 100, message = "氏名は100文字以内で入力してください")
    private String name;

    @NotBlank(message = "ふりがなを入力してください")
    @Size(max = 100, message = "ふりがなは100文字以内で入力してください")
    private String nameKana;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;
}
