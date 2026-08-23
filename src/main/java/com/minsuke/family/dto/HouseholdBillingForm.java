package com.minsuke.family.dto;

import com.minsuke.family.domain.SubscriptionStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseholdBillingForm {

    @Size(max = 100, message = "外部会員 ID は100文字以内で入力してください")
    private String externalMemberId;

    @NotNull(message = "サブスク状態を選択してください")
    private SubscriptionStatus subscriptionStatus;
}
