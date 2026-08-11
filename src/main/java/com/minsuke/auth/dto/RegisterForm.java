package com.minsuke.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterForm {

    @NotBlank(message = "メールアドレスを入力してください")
    @Email(message = "有効なメールアドレスを入力してください")
    private String email;

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
