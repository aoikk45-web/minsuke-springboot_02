package com.minsuke.event.exception;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException() {
        super("指定されたイベントが見つかりません");
    }
}
