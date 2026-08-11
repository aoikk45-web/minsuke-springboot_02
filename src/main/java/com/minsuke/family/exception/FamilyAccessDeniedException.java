package com.minsuke.family.exception;

public class FamilyAccessDeniedException extends RuntimeException {

    public FamilyAccessDeniedException() {
        super("この操作を行う権限がありません");
    }
}
