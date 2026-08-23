package com.minsuke.family.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.minsuke.family.domain.SubscriptionStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseholdDetailDTO {

    private Long id;
    private String name;
    private String nameKana;
    private String groupName;
    private String externalMemberId;
    private SubscriptionStatus subscriptionStatus;
    private List<ParentSummaryDTO> parents = new ArrayList<>();
    private List<ChildSummaryDTO> children = new ArrayList<>();

    @Getter
    @Setter
    public static class ParentSummaryDTO {
        private Long id;
        private String name;
        private String nameKana;
    }

    @Getter
    @Setter
    public static class ChildSummaryDTO {
        private Long id;
        private String name;
        private String nameKana;
        private LocalDate birthDate;
    }
}
