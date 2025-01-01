package com.maze.stulog.study.dto.request;

public record StudyUpdateRequest(
        String title,
        String description,
        int capacity
) {
}
