package com.maze.stulog.study.domain.repository;

import com.maze.stulog.study.domain.Participation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ParticipationJpaRepository extends JpaRepository<Participation, Long> {

    Optional<Participation> findByStudyIdAndMemberId(Long studyId, Long memberId);

    @Query("""
        DELETE FROM Participation p
        WHERE p.studyId = :studyId
    """)
    @Modifying
    void deleteByStudyId(Long studyId);

    @Query("""
        SELECT p from Participation p
        WHERE p.memberId = :memberId
    """)
    List<Participation> findAllByMemberId(Long memberId);
}
