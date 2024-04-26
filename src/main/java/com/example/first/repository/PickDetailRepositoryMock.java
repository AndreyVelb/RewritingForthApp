package com.example.first.repository;

import com.example.first.model.dto.CustomerAndDoorDto;
import com.example.first.model.dto.MaxLocDto;
import com.example.first.model.dto.PickDetailDto;

import java.util.List;
import java.util.Optional;

public class PickDetailRepositoryMock {

    public int getCountOfPickDetailByCaseIdAndStatus9(String caseId) {
        String selectCountByCaseIdAndStatus9 = SqlQuery.SELECT_COUNT_BY_CASE_ID_AND_STATUS_9; //We use this query
        return 2;
    }

    public Optional<PickDetailDto> getAllPickDetails(String caseId) {
        String selectAllPickDetailDto = SqlQuery.SELECT_PARAMS_FROM_PICKDETAIL_ORDERS_SKU;    //We use this query
        return Optional.of(PickDetailDto.builder()
                .storer("")
                .sku("")
                .DESCR("")
                .lotnum("")
                .id("")
                .consigneeKey("")
                .uom("")
                .qty(0.0d)
                .headerDoor("")
                .toLoc("")
                .loc("")
                .build());
    }

    public MaxLocDto getMaxLocDto() {
        String selectMaxToLocAndLoc = SqlQuery.SELECT_MAX_TO_LOC_AND_LOC;                   //We use this query
        return MaxLocDto.builder()
                .maxFromLoc("")
                .toLoc("")
                .build();
    }

    public CustomerAndDoorDto getCustomerAndDoorDto(String INid) {
        String selectCustomerAndDoor = SqlQuery.SELECT_CUSTOMER_AND_DOOR;                   //We use this query
        return CustomerAndDoorDto.builder()
                .consigneeKey("")
                .headerDoor("")
                .build();
    }

}
