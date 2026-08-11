package com.minsuke.instructor.exception;

public class InstructorNotFoundException extends RuntimeException {

    public InstructorNotFoundException() {
        super("指定された講師が見つかりません");
    }
}
