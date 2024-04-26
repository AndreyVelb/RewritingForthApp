package com.example.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class XLaneRow {

    private List<String> items;
    private String laneKey;

}
