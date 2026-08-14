package com.minsuke.event.domain;

public enum ParticipationUnit {
    HOUSEHOLD,
    PARENT,
    CHILD;

    public String label() {
        return switch (this) {
            case HOUSEHOLD -> "家庭";
            case PARENT -> "保護者";
            case CHILD -> "子ども";
        };
    }
}
