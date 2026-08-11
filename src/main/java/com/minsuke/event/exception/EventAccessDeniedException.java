package com.minsuke.event.exception;

public class EventAccessDeniedException extends RuntimeException {

    public EventAccessDeniedException() {
        super("この操作を行う権限がありません");
    }
}
