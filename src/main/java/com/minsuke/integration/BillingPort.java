package com.minsuke.integration;

import com.minsuke.family.domain.SubscriptionStatus;

/**
 * External billing / membership boundary. Loop 18 ships a no-op stub;
 * a later Loop may sync status from an external system.
 */
public interface BillingPort {

    SubscriptionStatus lookupSubscriptionStatus(String externalMemberId);
}
