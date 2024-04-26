package com.example.first.repository;

import com.example.first.model.dto.XPickDetailDto;

import java.util.Optional;

public class XPickDetailRepositoryMock {

    public Optional<XPickDetailDto> getAllXPickDetails(String childId) {
        String selectAllXPickDetailDto = SqlQuery.SELECT_BY_CHILD_ID;       //We use this query
        return Optional.of(XPickDetailDto.builder()
                .storer("")
                .sku("")
                .DESCR("")
                .lotnum("")
                .id("")
                .consigneeKey("")
                .uom("")
                .qty(0.0d)
                .build());
    }

}
