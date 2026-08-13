package com.minsuke.announcement.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnnouncementDetailDTO {

    private Long id;
    private String title;
    private String body;
    private LocalDateTime publishedAt;
    private boolean read;
}
