package com.maze.stulog.subscription.domain;

import static com.maze.stulog.common.error.ExceptionCode.NOT_MATCH_USER_AND_SUBSCRIPTION;

import com.maze.stulog.common.entity.BaseTimeEntity;
import com.maze.stulog.common.error.BusinessException;
import com.maze.stulog.schedule.domain.Calendar;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subscription extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private boolean checked;

    private Subscription(
            Calendar calendar,
            Long memberId
    ) {
        this.calendar = calendar;
        this.memberId = memberId;
        this.checked = true;
    }

    public static Subscription of(
            Calendar calendar,
            Long memberId
    ) {
        return new Subscription(calendar, memberId);
    }

    public void connectCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public void validateSubscriber(Long memberId) {
        if (this.memberId != memberId) {
            throw new BusinessException(NOT_MATCH_USER_AND_SUBSCRIPTION);
        }
    }

    public void toggleCheck() {
        checked ^= true;
    }
}
