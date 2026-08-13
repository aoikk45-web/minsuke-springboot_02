package com.minsuke.announcement.exception;

public class AnnouncementNotFoundException extends RuntimeException {

    public AnnouncementNotFoundException() {
        super("指定されたお知らせが見つかりません");
    }
}
