package com.maze.stulog.schedule.domain;

import com.maze.stulog.common.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE schedule SET deleted = true where id = ?")
public class Schedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    private boolean deleted;

    public static Schedule createSchedule(
            Calendar calendar,
            String title,
            String content,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {
        return new Schedule(
                calendar,
                title,
                content,
                startTime,
                endTime
        );
    }

    private Schedule(
            Calendar calendar,
            String title,
            String content,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {
        this.calendar = calendar;
        this.title = title;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
        this.deleted = false;
    }

    public void update(
            String title,
            String content,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {
       this.title = title;
       this.content = content;
       this.startTime = startTime;
       this.endTime = endTime;
    }
}
