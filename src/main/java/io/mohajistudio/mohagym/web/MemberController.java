package io.mohajistudio.mohagym.web;

import io.mohajistudio.mohagym.provider.service.MemberService;
import io.mohajistudio.mohagym.web.dto.ResponseMember;
import io.mohajistudio.mohagym.web.dto.ResponseMessage;
import io.mohajistudio.mohagym.web.dto.requestMember;
import io.mohajistudio.mohagym.web.dto.requestUserId;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<ResponseMessage> login(@Validated @RequestBody requestMember requestDto) { //@Validated로 요청 바디(@RequestBody)에 대한 유효성 검사//RequestMember.Member 객체(요청모델)로 변환합니다.
        ResponseMember.Token tokens = memberService.login(requestDto);

        return ResponseEntity.ok().body(ResponseMessage.builder()  //ResponseEntity 반환 즉 HTTP 응답을 생성//ResponseMessage 객체를 응답 바디로 설정하고 HTTP 상태 코드를 200 OK로 설정한 후 반환
                .message("로그인 성공")
                .data(tokens)
                .build());
    }

    @PostMapping("/admin/role")
    public ResponseEntity<ResponseMessage> changeRole(@Validated @RequestBody requestUserId requestDto ){
            String role = memberService.changeRole(requestDto);
            return ResponseEntity.ok().body(ResponseMessage.builder()
                    .message(role + "으로 권한 변경 성공")
                    .build());
    }

    @GetMapping("/logout")
    public ResponseEntity<ResponseMessage> logout(HttpServletRequest request){
        memberService.logout(request);
        return ResponseEntity.ok().body(ResponseMessage.builder()
                .message("로그아웃 완료")
                .build());
    }
}
