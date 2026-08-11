package com.minsuke.instructor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructorCardDTO {

    private Long id;
    private String name;
    private String nameKana;
    private String email;
    private String phone;
    private boolean active;
}
