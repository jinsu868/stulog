package com.maze.stulog.study.application;

import com.maze.stulog.member.domain.Member;
import com.maze.stulog.schedule.domain.Calendar;
import com.maze.stulog.schedule.domain.CalendarAuthority;
import com.maze.stulog.schedule.domain.CalendarRole;
import com.maze.stulog.schedule.domain.repository.CalendarAuthorityRepository;
import com.maze.stulog.schedule.domain.repository.CalendarRepository;
import com.maze.stulog.study.domain.Participation;
import com.maze.stulog.study.domain.Study;
import com.maze.stulog.study.domain.repository.ParticipationRepository;
import com.maze.stulog.study.domain.repository.StudyRepository;
import com.maze.stulog.study.dto.StudyCreateRequest;
import com.maze.stulog.subscription.domain.Subscription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudyService {

    private static final String STUDY_CALENDAR_PREFIX = "[STUDY] ";

    private final StudyRepository studyRepository;
    private final ParticipationRepository participationRepository;
    private final CalendarRepository calendarRepository;
    private final CalendarAuthorityRepository calendarAuthorityRepository;

    /**
     *
     * @param studyCreateRequest : 스터디 정보
     * @param member : 로그인한 유저 정보
     * @return
     */
    @Transactional
    public Long saveStudy(
            StudyCreateRequest studyCreateRequest,
            Member member
    ) {
        Study study = Study.of(
                studyCreateRequest.title(),
                studyCreateRequest.description(),
                studyCreateRequest.capacity()
        );
        studyRepository.save(study);

        Participation participation = Participation.of(
                member.getId(),
                study.getId(),
                true
        );
        participationRepository.save(participation);

        Calendar calendar = Calendar.from(STUDY_CALENDAR_PREFIX + studyCreateRequest.title());
        Subscription subscription = Subscription.of(calendar, member.getId());
        calendar.addSubscription(subscription);
        calendarRepository.save(calendar);

        CalendarAuthority calendarAuthority = CalendarAuthority.create(
                member.getId(),
                calendar.getId(),
                CalendarRole.ADMIN
        );
        calendarAuthorityRepository.save(calendarAuthority);

        return study.getId();
    }
}
