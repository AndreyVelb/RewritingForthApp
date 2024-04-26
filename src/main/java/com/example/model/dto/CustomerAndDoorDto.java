package com.example.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerAndDoorDto {

    private String consigneeKey;
    private String headerDoor;

}
