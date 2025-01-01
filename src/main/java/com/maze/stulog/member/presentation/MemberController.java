package com.maze.stulog.member.presentation;

import com.maze.stulog.auth.presentation.annotation.AuthUser;
import com.maze.stulog.member.application.MemberService;
import com.maze.stulog.member.domain.Member;
import com.maze.stulog.member.dto.request.MemberUpdateRequestWithoutImage;
import com.maze.stulog.member.dto.response.MemberDetailResponse;
import com.maze.stulog.member.facade.MemberUpdateFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberUpdateFacade memberUpdateFacade;
    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberDetailResponse> getMe(
            @AuthUser Member member
    ) {
        return ResponseEntity.ok(MemberDetailResponse.from(member));
    }

    @PatchMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> updateMember(
            @AuthUser Member member,
            @RequestPart MemberUpdateRequestWithoutImage memberUpdateRequestWithoutImage,
            @RequestPart MultipartFile file
    ) {
        memberUpdateFacade.updateMember(member, memberUpdateRequestWithoutImage, file);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDetailResponse> findById(
            @PathVariable Long memberId,
            @AuthUser Member member
    ) {
        return ResponseEntity.ok(memberService.findById(memberId));
    }
}
