package com.maze.stulog.study.domain.repository;

import com.maze.stulog.study.domain.Participation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationJpaRepository extends JpaRepository<Participation, Long> {

    Optional<Participation> findByStudyIdAndMemberId(Long studyId, Long memberId);

}
