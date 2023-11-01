//package io.mohajistudio.mohagym.provider.service;
//
//import io.mohajistudio.mohagym.core.security.role.Role;
//import io.mohajistudio.mohagym.entity.Member;
//import io.mohajistudio.mohagym.repository.MemberRepository;
//import io.mohajistudio.mohagym.web.dto.requestDto;
//import io.mohajistudio.mohagym.web.dto.requestToken;
//import io.mohajistudio.mohagym.web.dto.responseToken;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//@ActiveProfiles("test")
//@SpringBootTest
//public class requestAttendanceServiceImplTests {
//public class requestDtoServiceImplTests {
//
//    @Autowired
//    private MemberServiceImpl memberServiceImpl;
//    @Autowired
//    private MemberRepository memberRepository;
//
//
//    @Test
//    @Transactional
//    @DisplayName("회원가입 테스트(성공)")
//    public void registerAdminTest(){
//        requestDto.MemberProfile request = requestDto.MemberProfile.builder()
//                .email("test")
//                .password("1234")
//                .build();
//        memberServiceImpl.register(request);
//        assertNotNull(memberRepository.findByEmailAndDeletedAtIsNull("test"));
//    }
//
//
//    @Test
//    @Transactional
//    @DisplayName("로그인 테스트(성공)")
//    public void loginTest(){
//        //회원가입
//        requestDto.MemberProfile request = requestDto.MemberProfile.builder()
//                .email("test")
//                .password("1234")
//                .build();
//        memberServiceImpl.register(request);
//        //로그인
//        responseToken token = memberServiceImpl.login(request);
//        assertNotNull(token);
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("권한 바꾸기 테스트(성공)")
//    public void changeRoleTest(){
//        //회원가입1
//        requestDto.MemberProfile request = requestDto.MemberProfile.builder()
//                .email("admin")
//                .password("1234")
//                .build();
//        memberServiceImpl.register(request);
//        //권한 어드민으로 강제 변경
//        Member member = memberRepository.findByEmailAndDeletedAtIsNull("admin");
//        member.updateRole(Role.ADMIN.getCode());
//        memberRepository.save(member);
//        //회원가입2
//        requestDto.MemberProfile request2 = requestDto.MemberProfile.builder()
//                .email("user")
//                .password("1234")
//                .build();
//        memberServiceImpl.register(request2);
//        System.out.println(" 서비스 실행 전 user 권한 : " + Role.findByCode(memberRepository.findByEmailAndDeletedAtIsNull("user").getRole()).getDescription() );
//        //서비스 실행
//        //requestDto.requestMemberProfile request3 = new requestDto();
//       // request3.setEmail(request2.getEmail());
//
//
//       //  String usersRole = memberServiceImpl.changeRole(request3);
//        System.out.println("서비스 실행");
//       // System.out.println(" 서비스 실행 후 user 권한 : " +  usersRole);
//        //확인
//       // assertThat(usersRole).isEqualTo(Role.ADMIN.getDescription());
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("로그아웃(성공)")
//    public void logoutTest(){
//        //회원가입
//        requestDto.MemberProfile request = requestDto.MemberProfile.builder()
//                .email("test")
//                .password("1234")
//                .build();
//        memberServiceImpl.register(request);
//        //로그인
//        responseToken tokens = memberServiceImpl.login(request);
//        //requestToken 로 파싱
//        requestToken tokens2 = requestToken.builder()
//                .accessToken(tokens.getAccessToken())
//                .refreshToken(tokens.getRefreshToken()).build();
//        //로그아웃
//         memberServiceImpl.logout(tokens2);
//        Member logoutMember = memberRepository.findByEmailAndDeletedAtIsNull("test");
//        System.out.println("로그아웃 후 RefreshToken = " + logoutMember.getRefreshToken());
//        assertNull(logoutMember.getRefreshToken());
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("토큰 재발급 성공")
//    public void reissueTokenTest() throws InterruptedException {
//        //회원가입
//        requestDto.MemberProfile request = requestDto.MemberProfile.builder()
//                .email("test")
//                .password("1234")
//                .build();
//        memberServiceImpl.register(request);
//        //로그인
//        responseToken tokens = memberServiceImpl.login(request);
//        System.out.println("tokens = " + tokens);
//        //requestToken 로 파싱
//        requestToken tokens2 = requestToken.builder()
//                .accessToken(tokens.getAccessToken())
//                .refreshToken(tokens.getRefreshToken()).build();
//        System.out.println("tokens2 = " + tokens2);
//        Thread.sleep(1000); // 로그인과 동시에 재발급이 이루어 지면 만료 일이 같아서 토큰도 같음
//        //토큰 재발급
//        responseToken tokens3 = memberServiceImpl.reissueToken(tokens2);
//        System.out.println("tokens3 = " + tokens3);
//        //엑세스토큰 불일치 확인
//        assertThat(tokens2.getAccessToken()).isNotEqualTo(tokens3.getAccessToken());
//        //리프레시 토큰 불일치 확인
//        assertThat(tokens2.getRefreshToken()).isNotEqualTo(tokens3.getRefreshToken());
//
//    }
//}
