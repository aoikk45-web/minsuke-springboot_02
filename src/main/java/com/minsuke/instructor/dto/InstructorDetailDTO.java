package com.minsuke.instructor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructorDetailDTO {

    private Long id;
    private String name;
    private String nameKana;
    private String email;
    private String phone;
    private String notes;
    private boolean active;
    private InstructorWorkloadDTO workload;
}
