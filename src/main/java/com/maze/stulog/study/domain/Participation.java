package com.maze.stulog.study.domain;

import static com.maze.stulog.common.error.ExceptionCode.NOT_HOST_PARTICIPATION;

import com.maze.stulog.common.entity.BaseTimeEntity;
import com.maze.stulog.common.error.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UniqueMemberAndStudy",
                columnNames = {"memberId", "studyId"})
})
public class Participation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long studyId;

    @Column(nullable = false)
    private boolean isHost;

    public static Participation of(
            Long memberId,
            Long studyId,
            boolean isHost
    ) {
        return new Participation(
                memberId,
                studyId,
                isHost
        );
    }

    private Participation(
            Long memberId,
            Long studyId,
            boolean isHost
    ) {
        this.memberId = memberId;
        this.studyId = studyId;
        this.isHost = isHost;
    }

    public boolean isHost() {
        return isHost;
    }
}
