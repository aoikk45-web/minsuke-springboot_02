package com.minsuke.schedule.exception;

public class ScheduleNotFoundException extends RuntimeException {

    public ScheduleNotFoundException() {
        super("指定されたスケジュールが見つかりません");
    }
}
