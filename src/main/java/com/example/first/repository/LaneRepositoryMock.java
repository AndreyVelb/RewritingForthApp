package com.example.first.repository;

import com.example.first.model.entity.XLaneRow;

import java.util.List;

public class LaneRepositoryMock {

    public List<XLaneRow> getAllByConsigneeKey(String consigneeKey) {
        String selectAllPickDetailDto = SqlQuery.SELECT_LANES_BY_CONSIGNEE_KEY;             //We use this query
        return List.of(XLaneRow.builder().build());
    }

}
