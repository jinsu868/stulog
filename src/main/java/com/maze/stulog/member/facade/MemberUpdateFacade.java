package com.maze.stulog.member.facade;

import com.maze.stulog.member.application.MemberService;
import com.maze.stulog.member.domain.Member;
import com.maze.stulog.member.dto.request.MemberUpdateRequest;
import com.maze.stulog.member.dto.request.MemberUpdateRequestWithoutImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class MemberUpdateFacade {

    private final MemberService memberService;
    // S3Client 구현 필요, 트랜잭션 외부에서 이미지 업로드

    public void updateMember(
            Member member,
            MemberUpdateRequestWithoutImage memberUpdateRequestWithoutImage,
            MultipartFile file
    ) {

        //TODO : upload file

        var memberUpdateRequest = MemberUpdateRequest.of(
                memberUpdateRequestWithoutImage.name(),
                memberUpdateRequestWithoutImage.description(),
                "tmpImage"
        );
        memberService.updateMember(member, memberUpdateRequest);
    }
}
