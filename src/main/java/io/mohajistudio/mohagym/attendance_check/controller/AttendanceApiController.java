package io.mohajistudio.mohagym.attendance_check.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import io.mohajistudio.mohagym.attendance_check.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/attendance")
public class AttendanceApiController {
    private final MemberService memberService;
//    private final RequestDTO requestDTO;

    public AttendanceApiController(MemberService memberService ) {
        this.memberService = memberService;
    }


    //    @GetMapping("/attendance")
//    public String attendanceForm() {
//        log.info("return test.html");
//        return "test";
//    }
    @PostMapping("/check")
    public ResponseEntity<String> checkAttendance(@RequestBody Map<String ,String> requestData) {
        String name = requestData.get("name");
        boolean result = memberService.checkAttendance(name);
        log.info("success");
        if (result) {
            return ResponseEntity.ok("출석체크 성공");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원이 존재하지 않습니다.");
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        log.error("에러 발생: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
    }
}





