package com.maze.stulog.member.facade;

import com.maze.stulog.image.infrastructure.S3Client;
import com.maze.stulog.image.util.FileNameUtils;
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
    private final S3Client s3Client;

    public void updateMember(
            Member member,
            MemberUpdateRequestWithoutImage memberUpdateRequestWithoutImage,
            MultipartFile file
    ) {
        String uploadFileName = FileNameUtils.createFileName(file.getOriginalFilename());
        String imageUrl = s3Client.upload(file, uploadFileName);

        var memberUpdateRequest = MemberUpdateRequest.of(
                memberUpdateRequestWithoutImage.name(),
                memberUpdateRequestWithoutImage.description(),
                imageUrl
        );
        memberService.updateMember(member, memberUpdateRequest);
    }
}
