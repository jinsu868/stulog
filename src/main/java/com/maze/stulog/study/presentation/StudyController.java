package com.maze.stulog.study.presentation;

import com.maze.stulog.auth.presentation.annotation.AuthUser;
import com.maze.stulog.member.domain.Member;
import com.maze.stulog.study.application.StudyService;
import com.maze.stulog.study.dto.request.StudyCreateRequest;
import com.maze.stulog.study.dto.request.StudyUpdateRequest;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PatchMapping("/{studyId}")
    public ResponseEntity<Void> updateStudy(
            @PathVariable Long studyId,
            @AuthUser Member member,
            @RequestBody StudyUpdateRequest studyUpdateRequest
    ) {
        studyService.updateStudy(studyId, studyUpdateRequest, member);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{studyId}")
    public ResponseEntity<Void> deleteStudy(
            @PathVariable Long studyId,
            @AuthUser Member member
    ) {
        studyService.deleteStudy(studyId, member);

        return ResponseEntity.noContent().build();
    }
}
