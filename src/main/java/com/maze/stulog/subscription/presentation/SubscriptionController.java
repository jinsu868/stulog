package com.maze.stulog.subscription.presentation;

import com.maze.stulog.auth.presentation.annotation.AuthUser;
import com.maze.stulog.member.domain.Member;
import com.maze.stulog.subscription.application.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/subscriptions/{subscriptionId}/check")
    public ResponseEntity<Void> check(
            @PathVariable("subscriptionId") Long subscriptionId,
            @AuthUser Member member
    ) {
        subscriptionService.check(subscriptionId, member);

        return ResponseEntity.noContent().build();
    }
}
