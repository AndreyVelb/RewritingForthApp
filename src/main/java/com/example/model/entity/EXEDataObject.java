package com.example.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EXEDataObject {

    private String id;
    private String caseId;
    private String storer;
    private String lotNum;
    private String sku;
    private String descr;
    private String fromTag;
    private String fromLoc;
    private String toTag;
    private String toLoc;
    private double qty;
    private String uom;
    private String resultMessage;

}
