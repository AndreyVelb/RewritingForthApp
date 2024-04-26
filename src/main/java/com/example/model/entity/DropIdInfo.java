package com.example.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DropIdInfo {

    private String idType;
    private String dropLoc;
    private String childId;

}
