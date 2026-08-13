package com.minsuke.announcement.exception;

public class AnnouncementAccessDeniedException extends RuntimeException {

    public AnnouncementAccessDeniedException() {
        super("この操作を行う権限がありません");
    }
}
