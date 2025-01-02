package com.maze.stulog.member.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maze.stulog.auth.resolver.AuthUserArgumentResolver;
import com.maze.stulog.common.controller.ControllerTest;
import com.maze.stulog.member.application.MemberService;
import com.maze.stulog.member.domain.Member;
import com.maze.stulog.member.dto.request.MemberUpdateRequestWithoutImage;
import com.maze.stulog.member.dto.response.MemberDetailResponse;
import com.maze.stulog.member.facade.MemberUpdateFacade;
import jakarta.servlet.http.Cookie;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureRestDocs
@WebMvcTest(MemberController.class)
public class MemberControllerTest extends ControllerTest {

    @MockitoBean
    MemberService memberService;

    @MockitoBean
    MemberUpdateFacade memberUpdateFacade;

    @Autowired
    AuthUserArgumentResolver authUserArgumentResolver;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("로그인한 유저의 정보를 반환한다.")
    public void getMe() throws Exception {
        //given
        Member member = Member.of("socialId", "test_user", "https://test_image.png");
        var expect = MemberDetailResponse.from(member);

        Mockito.when(authUserArgumentResolver.supportsParameter(any()))
                        .thenReturn(true);
        Mockito.when(authUserArgumentResolver.resolveArgument(any(), any(), any(), any()))
                        .thenReturn(member);

        //when
        MvcResult result = mockMvc.perform(get("/api/v1/members/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "access-token")
                        .cookie(new Cookie("refresh-token", "refresh-token")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        var response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                MemberDetailResponse.class
        );

        //then
        Assertions.assertThat(response).usingRecursiveComparison().isEqualTo(expect);
    }

    @Test
    @DisplayName("다른 유저의 정보를 조회한다.")
    public void findById() throws Exception {
        //given
        Member member = Member.of("socialId", "test_user", "https://test_image.png");
        Long memberId = 1L;
        var expect = MemberDetailResponse.from(member);

        Mockito.when(memberService.findById(any()))
                .thenReturn(expect);

        //when
        MvcResult result = mockMvc.perform(get("/api/v1/members/" + memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "access-token")
                        .cookie(new Cookie("refresh-token", "refresh-token")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        var response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                MemberDetailResponse.class
        );

        //then
        Assertions.assertThat(response).usingRecursiveComparison().isEqualTo(expect);
    }

    @Test
    @DisplayName("로그인한 유저의 정보를 수정한다.")
    public void updateMember() throws Exception {
        //given
        var memberUpdateRequestWithoutImage = new MemberUpdateRequestWithoutImage("name", "description");

        MockMultipartFile request = new MockMultipartFile(
                "memberUpdateRequestWithoutImage",
                "memberUpdateRequestWithoutImage",
                APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(memberUpdateRequestWithoutImage)
        );

        MockMultipartFile image = new MockMultipartFile(
                "file",
                "profile.png",
                IMAGE_PNG_VALUE,
                "imageDummy".getBytes()
        );

        //when
        mockMvc.perform(MockMvcRequestBuilders.multipart(PATCH, "/api/v1/members")
                        .file(request)
                        .file(image)
                        .header(HttpHeaders.AUTHORIZATION, "access-token")
                        .cookie(new Cookie("refresh-token", "refresh-token")))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();

        //then
        verify(memberUpdateFacade).updateMember(any(), any(), any());
    }


}