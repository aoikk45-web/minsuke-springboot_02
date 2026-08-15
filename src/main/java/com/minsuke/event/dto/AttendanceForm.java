package com.minsuke.event.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceForm {

    private String action;
    private String participantType;
    private Long parentId;
    private Long childId;
    /** series = 同じスケジュールの今後分にも適用 */
    private String scope;
}
