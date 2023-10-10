package io.mohajistudio.mohagym.web;

import io.mohajistudio.mohagym.provider.service.MemberService;
import io.mohajistudio.mohagym.web.dto.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;


    //회원가입 로직
    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> register(@Validated @RequestBody requestMember requestDto) {
        memberService.register(requestDto);

        return ResponseEntity.ok().body(ResponseMessage.builder()
                .message("회원가입 성공")
                .build());
    }


    @PostMapping("/login") //경로//POST매핑
    public ResponseEntity<ResponseMessage> login(@Validated @RequestBody requestMember requestDto, HttpServletResponse response) { //@Validated로 요청 바디(@RequestBody)에 대한 유효성 검사//RequestMember.Member 객체(요청모델)로 변환합니다.
        responseMember tokens = memberService.login(requestDto);

        ResponseCookie cookie = setCookie(tokens.getRefreshToken());
        response.setHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok().body(ResponseMessage.builder()  //ResponseEntity 반환 즉 HTTP 응답을 생성//ResponseMessage 객체를 응답 바디로 설정하고 HTTP 상태 코드를 200 OK로 설정한 후 반환
                .message("로그인 성공")
                .data(tokens.getAccessToken())
                .build());
    }

    @PostMapping("/admin/role")
    public ResponseEntity<ResponseMessage> changeRole(@Validated @RequestBody requestUserId requestDto) {
        String role = memberService.changeRole(requestDto);
        return ResponseEntity.ok().body(ResponseMessage.builder()
                .message(role + "으로 권한 변경 성공")
                .build());
    }

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

    @GetMapping("/test")
    public ResponseEntity<ResponseMessage> test() {
        return ResponseEntity.ok().body(ResponseMessage.builder()
                .message("테스트 성공")
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
