package com.maze.stulog.study.presentation;

import com.maze.stulog.auth.presentation.annotation.AuthUser;
import com.maze.stulog.member.domain.Member;
import com.maze.stulog.study.application.StudyService;
import com.maze.stulog.study.dto.StudyCreateRequest;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/studies")
public class StudyController {

    private final StudyService studyService;

    @PostMapping
    public ResponseEntity<Void> createStudy(
            @AuthUser Member member,
            @RequestBody StudyCreateRequest studyCreateRequest
    ) {
        Long studyId = studyService.saveStudy(studyCreateRequest, member);

        return ResponseEntity.created(URI.create("api/v1/studies/" + studyId)).build();
    }


}
