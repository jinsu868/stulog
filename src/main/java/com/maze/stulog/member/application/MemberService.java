package com.maze.stulog.member.application;

import static com.maze.stulog.common.error.ExceptionCode.MEMBER_NOT_FOUND;

import com.maze.stulog.common.error.BusinessException;
import com.maze.stulog.member.domain.Member;
import com.maze.stulog.member.domain.MemberRepository;
import com.maze.stulog.member.dto.request.MemberUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void updateMember(
            Member loginMember,
            MemberUpdateRequest memberUpdateRequest
    ) {
        Member member = findMember(loginMember.getId());
        member.update(
                memberUpdateRequest.name(),
                memberUpdateRequest.description(),
                memberUpdateRequest.profile()
        );
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
    }
}
