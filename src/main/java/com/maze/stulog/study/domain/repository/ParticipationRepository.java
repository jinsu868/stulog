package com.maze.stulog.study.domain.repository;

import com.maze.stulog.study.domain.Participation;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ParticipationRepository {

    private final ParticipationJpaRepository participationJpaRepository;

    public Participation save(Participation participation) {
        return participationJpaRepository.save(participation);
    }

    public Optional<Participation> findByStudyIdAndMemberId(Long studyId, Long memberId) {
        return participationJpaRepository.findByStudyIdAndMemberId(studyId, memberId);
    }

    public void deleteByStudyId(Long studyId) {
        participationJpaRepository.deleteByStudyId(studyId);
    }

    public List<Participation> findAllByMemberId(Long memberId) {
        return participationJpaRepository.findAllByMemberId(memberId);
    }
}
