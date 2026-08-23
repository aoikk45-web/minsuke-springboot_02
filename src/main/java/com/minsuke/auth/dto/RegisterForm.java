package com.minsuke.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterForm {

    @NotBlank(message = "アカウント ID を入力してください")
    @Size(min = 3, max = 50, message = "アカウント ID は3〜50文字で入力してください")
    @Pattern(
            regexp = "^[a-zA-Z0-9][a-zA-Z0-9_-]*$",
            message = "アカウント ID は英数字・ハイフン・アンダースコアのみ使用できます")
    private String loginId;

    @NotBlank(message = "パスワードを入力してください")
    @Size(min = 8, message = "パスワードは8文字以上で入力してください")
    private String password;

    @NotBlank(message = "パスワード（確認）を入力してください")
    private String confirmPassword;

    @NotBlank(message = "家族名を入力してください")
    @Size(max = 100, message = "家族名は100文字以内で入力してください")
    private String householdName;

    @NotBlank(message = "ふりがなを入力してください")
    @Size(max = 100, message = "ふりがなは100文字以内で入力してください")
    private String householdNameKana;

    @Size(max = 50, message = "班名は50文字以内で入力してください")
    private String groupName;
}
