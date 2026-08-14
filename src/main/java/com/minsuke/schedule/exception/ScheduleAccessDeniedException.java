package com.minsuke.schedule.exception;

public class ScheduleAccessDeniedException extends RuntimeException {

    public ScheduleAccessDeniedException() {
        super("この操作を行う権限がありません");
    }
}
