package com.minsuke.family.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseholdCardDTO {

    private Long id;
    private String name;
    private String nameKana;
    private String groupName;
    private long parentCount;
    private long childCount;
}
