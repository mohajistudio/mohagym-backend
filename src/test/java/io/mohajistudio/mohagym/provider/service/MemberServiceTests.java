package io.mohajistudio.mohagym.provider.service;

import io.mohajistudio.mohagym.core.security.role.Role;
import io.mohajistudio.mohagym.entity.Member;
import io.mohajistudio.mohagym.repository.MemberRepository;
import io.mohajistudio.mohagym.web.dto.RequestMember;
import io.mohajistudio.mohagym.web.dto.ResponseMember;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
@ActiveProfiles("test")
@SpringBootTest
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;


    @Test
    @Transactional
    @DisplayName("회원가입 테스트(성공)")
    public void registerAdminTest(){
        RequestMember.Member request = RequestMember.Member.builder()
                .userId("test")
                .password("1234")
                .build();
        memberService.register(request);
        assertNotNull(memberRepository.findByUserId("test"));
    }


    @Test
    @Transactional
    @DisplayName("로그인 테스트(성공)")
    public void loginTest(){
        //회원가입
        RequestMember.Member request = RequestMember.Member.builder()
                .userId("test")
                .password("1234")
                .build();
        memberService.register(request);
        //로그인
        ResponseMember.Token token = memberService.login(request);
        assertNotNull(token);
    }

    @Test
    @Transactional
    @DisplayName("권한 바꾸기 테스트(성공)")
    public void changeRole(){
        //회원가입1
        RequestMember.Member request = RequestMember.Member.builder()
                .userId("admin")
                .password("1234")
                .build();
        memberService.register(request);
        //권한 어드민으로 강제 변경
        Member member = memberRepository.findByUserId("admin");
        member.setRole(Role.ADMIN.getCode());
        memberRepository.save(member);
        //회원가입2
        RequestMember.Member request2 = RequestMember.Member.builder()
                .userId("user")
                .password("1234")
                .build();
        memberService.register(request2);
        System.out.println(" 서비스 실행 전 user 권한 : " + Role.findByCode(memberRepository.findByUserId("user").getRole()).getDescription() );
        //서비스 실행
         String usersRole = memberService.changeRole(request,request2);
        System.out.println("서비스 실행");
        System.out.println(" 서비스 실행 후 user 권한 : " +  usersRole);
        //확인
        Assertions.assertThat(usersRole).isEqualTo(Role.ADMIN.getDescription());
    }
}
