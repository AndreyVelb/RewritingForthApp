package com.example.first.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransshipDto {

    private String consigneeKey;
    private double qty;

}
