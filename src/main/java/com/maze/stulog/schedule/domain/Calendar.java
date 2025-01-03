package com.maze.stulog.schedule.domain;

import com.maze.stulog.common.entity.BaseTimeEntity;
import com.maze.stulog.subscription.domain.Subscription;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE calendar SET deleted = true where id = ?")
public class Calendar extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subscription> subscriptions = new ArrayList<>();

    @OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();

    private boolean deleted;

    private Calendar(String name) {
        this.name = name;
        this.deleted = false;
    }

    public static Calendar from(String name) {
        return new Calendar(name);
    }

    public void addSubscription(Subscription subscription) {
        subscription.connectCalendar(this);
        subscriptions.add(subscription);
    }

    public void updateName(String name) {
        this.name = name;
    }
}
