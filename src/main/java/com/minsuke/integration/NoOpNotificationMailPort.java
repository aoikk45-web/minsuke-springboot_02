package com.minsuke.integration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NoOpNotificationMailPort implements NotificationMailPort {

    private static final Logger log = LoggerFactory.getLogger(NoOpNotificationMailPort.class);

    @Override
    public void sendAnnouncement(List<String> externalMemberIds, String title, String body) {
        log.debug("NotificationMailPort stub: skip email for {} households, title={}",
                externalMemberIds == null ? 0 : externalMemberIds.size(), title);
    }
}
