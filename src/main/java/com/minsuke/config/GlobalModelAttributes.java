package com.minsuke.config;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.minsuke.announcement.service.AnnouncementService;
import com.minsuke.auth.security.MinsukeUserDetails;

@ControllerAdvice
public class GlobalModelAttributes {

    private final AnnouncementService announcementService;

    public GlobalModelAttributes(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @ModelAttribute("currentUser")
    public MinsukeUserDetails currentUser(@AuthenticationPrincipal MinsukeUserDetails userDetails) {
        return userDetails;
    }

    @ModelAttribute("unreadAnnouncementCount")
    public long unreadAnnouncementCount(@AuthenticationPrincipal MinsukeUserDetails userDetails) {
        return announcementService.countUnread(userDetails);
    }
}
