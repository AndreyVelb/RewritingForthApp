package com.example.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MaxLocDto {

    private String maxFromLoc;
    private String toLoc;

}
