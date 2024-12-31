package com.maze.stulog.study.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private int capacity;

    public static Study of(
            String title,
            String description,
            int capacity
    ) {
        return new Study(
                title,
                description,
                capacity
        );
    }

    private Study(
            String title,
            String description,
            int capacity
    ) {
        this.title = title;
        this.description = description;
        this.capacity = capacity;
    }
}
