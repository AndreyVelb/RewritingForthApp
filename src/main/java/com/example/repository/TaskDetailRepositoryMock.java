package com.example.repository;

import com.example.model.dto.TaskDetailDto;

import java.util.Optional;

public class TaskDetailRepositoryMock {

    public Optional<TaskDetailDto> getAllByTOIdStatus0AndTaskTypeMV(String toId) {
        String selectTaskDetailsByToIdStatus0Mv = SqlQuery.SELECT_TASK_DETAILS_BY_TO_ID_STATUS_0_MV;        //We use this query
        return Optional.of(TaskDetailDto.builder()
                .caseId("")
                .storerKey("")
                .sku("")
                .descr("")
                .lot("")
                .fromId("")
                .fromLoc("")
                .toId("")
                .toloc("")
                .qty(0.0d)
                .uom("")
                .build());
    }

}
