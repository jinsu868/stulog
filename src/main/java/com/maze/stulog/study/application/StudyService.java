package com.maze.stulog.study.application;

import static com.maze.stulog.common.error.ExceptionCode.CALENDAR_NOT_FOUND;
import static com.maze.stulog.common.error.ExceptionCode.NOT_HOST_PARTICIPATION;
import static com.maze.stulog.common.error.ExceptionCode.PARTICIPATION_NOT_FOUND;
import static com.maze.stulog.common.error.ExceptionCode.STUDY_NOT_FOUND;

import com.maze.stulog.common.error.BusinessException;
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
import com.maze.stulog.study.dto.request.StudyCreateRequest;
import com.maze.stulog.study.dto.request.StudyUpdateRequest;
import com.maze.stulog.subscription.domain.Subscription;
import java.util.Optional;
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
     * @return : 생성한 스터디 Id
     */
    @Transactional
    public Long saveStudy(
            StudyCreateRequest studyCreateRequest,
            Member member
    ) {
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

        Study study = Study.of(
                calendar.getId(),
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

        return study.getId();
    }

    /**
     *
     * @param studyId : 수정할 스터디 Id
     * @param studyUpdateRequest : 수정할 스터디 정보
     * @param member : 로그인한 유저 정보
     */
    @Transactional
    public void updateStudy(
            Long studyId,
            StudyUpdateRequest studyUpdateRequest,
            Member member
    ) {
        Participation participation = findParticipationByStudyAndMember(studyId, member.getId());
        validateStudyUpdate(participation);

        Study study = findStudy(studyId);
        study.update(
                studyUpdateRequest.title(),
                studyUpdateRequest.description(),
                studyUpdateRequest.capacity()
        );

        Calendar calendar = findCalendar(study);
        calendar.updateName(STUDY_CALENDAR_PREFIX + studyUpdateRequest.title());
    }

    private Calendar findCalendar(Study study) {
        return calendarRepository.findById(study.getCalendarId())
                .orElseThrow(() -> new BusinessException(CALENDAR_NOT_FOUND));
    }

    private Study findStudy(Long studyId) {
        return studyRepository.findById(studyId)
                .orElseThrow(() -> new BusinessException(STUDY_NOT_FOUND));
    }

    private Participation findParticipationByStudyAndMember(Long studyId, Long memberId) {
        return participationRepository.findByStudyIdAndMemberId(studyId, memberId)
                .orElseThrow(() -> new BusinessException(PARTICIPATION_NOT_FOUND));
    }

    private static void validateStudyUpdate(Participation participation) {
        if (!participation.isHost()) {
            throw new BusinessException(NOT_HOST_PARTICIPATION);
        }
    }
}
