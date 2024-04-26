package com.example.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class TaskDetailDto {

    private String caseId;
    private String storerKey;
    private String sku;
    private String descr;
    private String lot;
    private String fromId;
    private String fromLoc;
    private String toId;
    private String toloc;
    private double qty;
    private String uom;

}
