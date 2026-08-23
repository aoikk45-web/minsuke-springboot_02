package com.minsuke.integration;

import org.springframework.stereotype.Component;

import com.minsuke.family.domain.SubscriptionStatus;

@Component
public class NoOpBillingPort implements BillingPort {

    @Override
    public SubscriptionStatus lookupSubscriptionStatus(String externalMemberId) {
        return SubscriptionStatus.UNKNOWN;
    }
}
