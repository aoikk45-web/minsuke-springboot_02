package com.minsuke.event.exception;

public class EventCapacityFullException extends RuntimeException {

    public EventCapacityFullException() {
        super("定員に達しているため参加登録できません");
    }
}
