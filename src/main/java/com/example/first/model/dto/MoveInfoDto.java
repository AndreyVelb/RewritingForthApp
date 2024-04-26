package com.example.first.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class MoveInfoDto {

    private String toId;
    private String fromLoc;
    private String toLoc;
    private String packKey;
    private String refNum;
    private String movLogKey;
    private String caseId;
    private String storer;
    private String lotNum;
    private String sku;
    private String descr;
    private String fromTag;
    private String toTag;
    private double qty;
    private String uom;


}
