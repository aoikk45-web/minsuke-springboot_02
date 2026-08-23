package com.minsuke.integration;

import java.util.List;

/**
 * External announcement email sender. Loop 18 ships a no-op stub;
 * MinSuke does not hold SMTP or recipient emails.
 */
public interface NotificationMailPort {

    void sendAnnouncement(List<String> externalMemberIds, String title, String body);
}
