package com.minsuke.instructor.exception;

public class InstructorAccessDeniedException extends RuntimeException {

    public InstructorAccessDeniedException() {
        super("この操作を行う権限がありません");
    }
}
