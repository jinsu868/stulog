package com.maze.stulog.study.domain.repository;

import com.maze.stulog.study.domain.Study;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudyJpaRepository extends JpaRepository<Study, Long> {

    @Query("""
        SELECT s FROM Study s
        WHERE s.id in :studyIds
        ORDER BY s.id DESC
    """)
    List<Study> findAllStudiesInIds(List<Long> studyIds);
}
