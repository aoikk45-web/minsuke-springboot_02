package com.minsuke.announcement.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnnouncementCardDTO {

    private Long id;
    private String title;
    private LocalDateTime publishedAt;
    private boolean read;
}
