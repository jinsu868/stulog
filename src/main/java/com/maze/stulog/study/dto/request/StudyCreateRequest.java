package com.maze.stulog.study.dto.request;

public record StudyCreateRequest(
        String title,
        String description,
        int capacity
) {
}
