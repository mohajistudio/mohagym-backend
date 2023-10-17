package io.mohajistudio.mohagym.web;

import io.mohajistudio.mohagym.entity.Member;
import io.mohajistudio.mohagym.provider.service.MemberService;
import io.mohajistudio.mohagym.web.dto.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    //케프 끝나고 상용화 할때 회원 가입시 전화번호 uuid 보내서 인증하는 로직 추가 해보기 //sms, 이메일로 uuid 전송하는 api 서비스 찾아야함//건당 과금이 필요하기에 회의를 통해 결정
    //전화번호 수정할때도 인증해야함//전화번호나 이메일 골라서
    //비번 변경 기능 추가하고 인증// 전화번호나 이메일 골라서


    //회원가입 로직
    @PostMapping("/members")
    public ResponseEntity<ResponseMessage> register(@Validated @RequestBody requestDto.MemberProfile requestDto) {
        memberService.register(requestDto);

        return ResponseEntity.ok().body(ResponseMessage.builder()
                .message("회원가입 성공")
                .build());
    }

    //로그인
    @PostMapping("/login") //경로//POST매핑
    public ResponseEntity<ResponseMessage> login(@Validated @RequestBody requestDto requestDto, HttpServletResponse response) { //@Validated로 요청 바디(@RequestBody)에 대한 유효성 검사//RequestMember.Member 객체(요청모델)로 변환합니다.
        responseMember tokens = memberService.login(requestDto);

        ResponseCookie cookie = setCookie(tokens.getRefreshToken());
        response.setHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok().body(ResponseMessage.builder()  //ResponseEntity 반환 즉 HTTP 응답을 생성//ResponseMessage 객체를 응답 바디로 설정하고 HTTP 상태 코드를 200 OK로 설정한 후 반환
                .message("로그인 성공")
                .data(tokens.getAccessToken())
                .build());
    }

    //역할변경
    @PostMapping("/role")
    public ResponseEntity<ResponseMessage> changeRole(@Validated @RequestBody String name) {
        String role = memberService.changeRole(name);
        return ResponseEntity.ok().body(ResponseMessage.builder()
                .message(role + "으로 권한 변경 성공")
                .build());
    }

    //로그아웃
    @GetMapping("/logout")
    public ResponseEntity<ResponseMessage> logout(HttpServletRequest request) {
        requestToken token = requestToken.builder()
                .accessToken(request.getHeader("Authorization"))
                .build();
        memberService.logout(token);
        return ResponseEntity.ok().body(ResponseMessage.builder()
                .message("로그아웃 완료")
                .build());
    }

    //토큰 재발급
    @GetMapping("/token")
    public ResponseEntity<ResponseMessage> reissueToken(HttpServletRequest request, HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();
        String refreshToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    // "refreshToken" 쿠키를 찾았으면 해당 쿠키의 값을 반환합니다.
                    refreshToken = cookie.getValue();
                }
            }
        }
        requestToken oldTokens = requestToken.builder()
                .accessToken(request.getHeader("Authorization"))
                .refreshToken(refreshToken)
                .build();

        responseMember newTokens = memberService.reissueToken(oldTokens);

        ResponseCookie cookie = setCookie(newTokens.getRefreshToken());
        response.setHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok().body(ResponseMessage.builder()
                .message("재발급성공")
                .data(newTokens.getAccessToken())
                .build());
    }


    //전체조회 혹은 이름으로 검색
    @GetMapping("/members")
    public ResponseEntity<List<Member>> getAllMembers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, @RequestParam(required = false) String name
    //localhost:8080/members?page=0&size=20 과 동일
    ) {

        if (name != null) {
            Page<Member> membersPage = memberService.getMemberByName(page, size,name);
            List<Member> members =  membersPage.getContent();
            return ResponseEntity.ok(members);
        } else {
            Page<Member> membersPage = memberService.getAllMembers(page, size);
            List<Member> members =  membersPage.getContent();
            return ResponseEntity.ok(members);
        }

    }


    //멤버 아이디로 멤버 프로필 가져와서 보여주기
    //멤버 프로필을 jsonignore 처리 했음으로 자세한 데이터를 포함하지 않음 이를 해결 하려면 결국 jsonignore 를 풀고
    //위에서 response dto로 제한하는 방법밖에 없음 아니면 전체조회나 이름 조회시 프로필정보를 반환하지 않도록 하는 방법도 있음
    //이에 대해 창희형과 이야기 해봐야 할 것 같음
  /*  @GetMapping("/members/{memberId}")
    public ResponseEntity<MemberProfile> getMemberById(@PathVariable Long memberId) {
        // memberId를 사용하여 회원 정보를 조회하고 반환
        MemberProfile memberProfile = memberService.getMemberProfileById(memberId);
        if (memberProfile != null) {
            return ResponseEntity.ok(memberProfile);
        } else {
            // 해당 memberId에 해당하는 회원이 없을 경우 404 에러 반환
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
    }*/


    //회원 탈퇴
    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<ResponseMessage> disableMember(@PathVariable Long memberId){
        memberService.disableMember(memberId);

            return ResponseEntity.ok().body(ResponseMessage.builder()
                    .message("회원 탈퇴 완료")
                    .build());

    }
    //이름변경
    @PostMapping("/name")
    public ResponseEntity<ResponseMessage> changeName(requestProfile.name dto){
        memberService.changeName(dto);
        return ResponseEntity.ok().body(ResponseMessage.builder()
                .message("이름 변경 완료")
                .build());
    }
    //전화번호 변경
    @PostMapping("/phoneNo")
    public ResponseEntity<ResponseMessage> changeName(requestProfile.phoneNo dto){
        memberService.changePhoneNo(dto);
        return ResponseEntity.ok().body(ResponseMessage.builder()
                .message("전화번호 완료")
                .build());
    }

    private ResponseCookie setCookie(String token) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", token)
                // 토큰의 유효 기간//30일
                .maxAge(30 * 24 * 60 * 60)
                .path("/")
                // https 환경에서만 쿠키가 발동합니다.
                .secure(false)
                // 동일 사이트과 크로스 사이트에 모두 쿠키 전송이 가능합니다
                .sameSite("None")
                .httpOnly(true)
                // 브라우저에서 쿠키에 접근할 수 없도록 제한
                .build();
        return cookie;
    }


}
