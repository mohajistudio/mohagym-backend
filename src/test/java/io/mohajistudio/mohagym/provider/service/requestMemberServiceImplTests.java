package io.mohajistudio.mohagym.provider.service;

import io.mohajistudio.mohagym.core.security.role.Role;
import io.mohajistudio.mohagym.entity.Member;
import io.mohajistudio.mohagym.repository.MemberRepository;
import io.mohajistudio.mohagym.web.dto.responseMember;
import io.mohajistudio.mohagym.web.dto.requestMember;
import io.mohajistudio.mohagym.web.dto.requestUserId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ActiveProfiles("test")
@SpringBootTest
public class requestMemberServiceImplTests {

    @Autowired
    private MemberServiceImpl memberServiceImpl;
    @Autowired
    private MemberRepository memberRepository;


    @Test
    @Transactional
    @DisplayName("회원가입 테스트(성공)")
    public void registerAdminTest(){
        requestMember request = requestMember.builder()
                .userId("test")
                .password("1234")
                .build();
        memberServiceImpl.register(request);
        assertNotNull(memberRepository.findByUserId("test"));
    }


    @Test
    @Transactional
    @DisplayName("로그인 테스트(성공)")
    public void loginTest(){
        //회원가입
        requestMember request = requestMember.builder()
                .userId("test")
                .password("1234")
                .build();
        memberServiceImpl.register(request);
        //로그인
        responseMember token = memberServiceImpl.login(request);
        assertNotNull(token);
    }

    @Test
    @Transactional
    @DisplayName("권한 바꾸기 테스트(성공)")
    public void changeRoleTest(){
        //회원가입1
        requestMember request = requestMember.builder()
                .userId("admin")
                .password("1234")
                .build();
        memberServiceImpl.register(request);
        //권한 어드민으로 강제 변경
        Member member = memberRepository.findByUserId("admin");
        member.setRole(Role.ADMIN.getCode());
        memberRepository.save(member);
        //회원가입2
        requestMember request2 = requestMember.builder()
                .userId("user")
                .password("1234")
                .build();
        memberServiceImpl.register(request2);
        System.out.println(" 서비스 실행 전 user 권한 : " + Role.findByCode(memberRepository.findByUserId("user").getRole()).getDescription() );
        //서비스 실행
        requestUserId request3 = requestUserId.builder().userId(request2.getUserId()).build();
         String usersRole = memberServiceImpl.changeRole(request3);
        System.out.println("서비스 실행");
        System.out.println(" 서비스 실행 후 user 권한 : " +  usersRole);
        //확인
        Assertions.assertThat(usersRole).isEqualTo(Role.ADMIN.getDescription());
    }

    @Test
    @Transactional
    @DisplayName("로그아웃(성공)")
    public void logoutTest(){
        //회원가입
        requestMember request = requestMember.builder()
                .userId("test")
                .password("1234")
                .build();
        memberServiceImpl.register(request);
        //로그인
        responseMember token = memberServiceImpl.login(request);
        //로그아웃
        //목 MockHttpServletRequest 객체 설정
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addHeader("x-auth-token",token.getAccessToken() );
        memberServiceImpl.logout(mockRequest);
        Member logoutMember = memberRepository.findByUserId("test");
        System.out.println("로그아웃 후 RefreshToken = " + logoutMember.getRefreshToken());
        assertNull(logoutMember.getRefreshToken());
    }
}
