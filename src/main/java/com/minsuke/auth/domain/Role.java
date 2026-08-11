package com.minsuke.auth.domain;

public enum Role {
    ADMIN,
    PARENT;

    public String getAuthority() {
        return "ROLE_" + name();
    }
}
