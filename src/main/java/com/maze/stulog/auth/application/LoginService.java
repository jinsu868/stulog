package com.maze.stulog.auth.application;

import com.maze.stulog.auth.domain.AuthTokens;
import com.maze.stulog.auth.domain.RefreshToken;
import com.maze.stulog.auth.domain.RefreshTokenRepository;
import com.maze.stulog.auth.dto.request.LoginRequest;
import com.maze.stulog.auth.infrastructure.KakaoOAuthProvider;
import com.maze.stulog.auth.infrastructure.KakaoUserInfo;
import com.maze.stulog.auth.util.JwtUtil;
import com.maze.stulog.common.error.BusinessException;
import com.maze.stulog.common.error.ExceptionCode;
import com.maze.stulog.member.domain.Member;
import com.maze.stulog.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final KakaoOAuthProvider kakaoOAuthProvider;

    public AuthTokens login(LoginRequest loginRequest) {
        String kakaoAccessToken = kakaoOAuthProvider.fetchKakaoAccessToken(loginRequest.code());
        KakaoUserInfo userInfo = kakaoOAuthProvider.getUserInfo(kakaoAccessToken);

        Member member = findOrCreateMember(
               userInfo.getSocialId(),
               userInfo.getNickname(),
                userInfo.getProfileImageUrl()
        );

        AuthTokens authTokens = jwtUtil.createLoginToken(member.getId().toString());
        RefreshToken refreshToken = new RefreshToken(member.getId(), authTokens.refreshToken());
        refreshTokenRepository.save(refreshToken);
        return authTokens;
    }

    public void logout(String refreshToken) {
        refreshTokenRepository.deleteById(refreshToken);
    }

    public String reissueAccessToken(String refreshToken, String authHeader) {
        //Bearer 제거
        String accessToken = authHeader.split(" ")[1];

        //토큰 만료, 비밀키 무결성 검사
        jwtUtil.validateRefreshToken(refreshToken);

        //Access Token이 유효한 경우 -> 재반환
        if (jwtUtil.isAccessTokenValid(accessToken)) {
            return accessToken;
        }

        //Access Token이 만료된 경우 -> Refresh Token DB 검증 후 재발급
        if (jwtUtil.isAccessTokenExpired(accessToken)) {
            RefreshToken foundRefreshToken = refreshTokenRepository.findById(refreshToken)
                    .orElseThrow(() -> new BusinessException(ExceptionCode.INVALID_REFRESH_TOKEN));
            return jwtUtil.reissueAccessToken(foundRefreshToken.getUserId().toString());
        }

        throw new BusinessException(ExceptionCode.FAILED_TO_VALIDATE_TOKEN);
    }

    private Member findOrCreateMember(String socialId, String nickname, String profileImageUrl) {
        return memberRepository.findBySocialId(socialId)
                .orElseGet(() -> createUser(socialId, nickname, profileImageUrl));
    }

    private Member createUser(String socialId, String nickname, String profileImageUrl) {
        String nickName = generateNewUserNickname(socialId, nickname);
        log.info("nickname={}", nickName);
        return memberRepository.save(Member.of(socialId, nickName, profileImageUrl));
    }

    private String generateNewUserNickname(String socialLoginId, String nickname) {
        return nickname + "#" + socialLoginId;
    }
}
