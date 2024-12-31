package com.maze.stulog.study.domain.repository;

import com.maze.stulog.study.domain.Study;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StudyRepository {

    private final StudyJpaRepository studyJpaRepository;

    public Study save(Study study) {
        return studyJpaRepository.save(study);
    }
}
