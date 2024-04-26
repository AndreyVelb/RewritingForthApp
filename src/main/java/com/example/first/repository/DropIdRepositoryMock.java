package com.example.first.repository;

import com.example.first.model.entity.DropIdInfo;

import java.util.List;

public class DropIdRepositoryMock {

    public int getCountByDropId(String INId) {
        String selectCountDropByDropId = SqlQuery.SELECT_COUNT_DROP_BY_DROPID;          //We use this query
        return 3;
    }

    public List<DropIdInfo> getAllByDropIdOrderDesc(String dropId) {
        String selectDropIdDetailByDropId = SqlQuery.SELECT_DROPIDDETAIL_BY_DROPID_ORDER_BY_EDITDATE_DESC;  //We use this query
        return List.of(DropIdInfo.builder()
                        .idType("")
                        .dropLoc("")
                        .childId("")
                .build());
    }

    public String getStatus(String dropId) {
        String selectStatusByDropId = SqlQuery.SELECT_DROPID_STATUS_BY_DROPID;          //We use this query
        return "1";
    }

}
