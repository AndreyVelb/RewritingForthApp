package com.example.repository;

import com.example.model.dto.TransshipDto;

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
