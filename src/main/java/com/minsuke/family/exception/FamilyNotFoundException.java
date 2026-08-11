package com.minsuke.family.exception;

public class FamilyNotFoundException extends RuntimeException {

    public FamilyNotFoundException() {
        super("指定された家族が見つかりません");
    }
}
