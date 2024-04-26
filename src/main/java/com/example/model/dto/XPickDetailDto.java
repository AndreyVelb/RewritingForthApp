package com.example.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class XPickDetailDto {

    private String storer;
    private String sku;
    private String DESCR;
    private String lotnum;
    private String id;
    private String consigneeKey;
    private String uom;
    private double qty;

}
