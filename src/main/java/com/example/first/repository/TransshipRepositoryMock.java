package com.example.first.repository;

import com.example.first.model.dto.TransshipDto;

import java.util.List;
import java.util.Optional;

public class TransshipRepositoryMock {

    public Optional<TransshipDto> getTransshipDetails(String childId) {
        String selectDetailFromTransship = SqlQuery.SELECT_DETAIL_FROM_TRANSSHIP;           //We use this query
        return Optional.of(TransshipDto.builder()
                .consigneeKey("")
                .qty(0.0d)
                .build());
    }

}
