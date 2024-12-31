package com.maze.stulog.study.domain.repository;

import com.maze.stulog.study.domain.Participation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ParticipationRepository {

    private final ParticipationJpaRepository participationJpaRepository;

    public Participation save(Participation participation) {
        return participationJpaRepository.save(participation);
    }
}
