package com.example.first.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class PickDetailDto {

    private String storer;
    private String sku;
    private String DESCR;
    private String lotnum;
    private String id;
    private String consigneeKey;
    private String uom;
    private double qty;
    private String headerDoor;
    private String toLoc;
    private String loc;

}
