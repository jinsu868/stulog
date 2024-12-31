package com.maze.stulog.study.domain.repository;

import com.maze.stulog.study.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyJpaRepository extends JpaRepository<Study, Long> {
}
