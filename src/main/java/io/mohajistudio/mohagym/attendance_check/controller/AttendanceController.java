package io.mohajistudio.mohagym.attendance_check.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import io.mohajistudio.mohagym.attendance_check.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class AttendanceController {
    private final MemberService memberService;

    @Autowired
    public AttendanceController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/attendance")
    public String attendanceForm() {
        System.out.println("잘 넘어오네");
        return "test";
    }

    @PostMapping("/check-attendance")
    public String checkAttendance(@RequestParam String name, Model model) {
        boolean result = memberService.checkAttendance(name);
        if (result) {
            model.addAttribute("message", "출석체크 성공");
        } else {
            model.addAttribute("message", "회원이 존재하지 않습니다.");
        }
        System.out.println("결과 넘어오나");
        return "result";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {

        return "error";
    }
}
