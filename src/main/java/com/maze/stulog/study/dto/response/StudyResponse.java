package com.maze.stulog.study.dto.response;

import com.maze.stulog.study.domain.Study;

public record StudyResponse(
        Long id,
        String title,
        String description,
        int capacity
) {

    public static StudyResponse from(Study study) {
        return new StudyResponse(
                study.getId(),
                study.getTitle(),
                study.getDescription(),
                study.getCapacity()
        );
    }
}
