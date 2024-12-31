package com.maze.stulog.study.dto;

public record StudyCreateRequest(
        String title,
        String description,
        int capacity
) {
}
