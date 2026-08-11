package com.minsuke.family.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseholdForm {

    @NotBlank(message = "家族名を入力してください")
    @Size(max = 100, message = "家族名は100文字以内で入力してください")
    private String name;

    @NotBlank(message = "ふりがなを入力してください")
    @Size(max = 100, message = "ふりがなは100文字以内で入力してください")
    private String nameKana;

    @Size(max = 50, message = "班名は50文字以内で入力してください")
    private String groupName;
}
