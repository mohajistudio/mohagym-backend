package io.mohajistudio.mohagym.attendance_check.controller;


import io.mohajistudio.mohagym.attendance_check.dto.AttendanceCheckDTO;
import io.mohajistudio.mohagym.attendance_check.dto.CheckedMemberDTO;
import io.mohajistudio.mohagym.attendance_check.service.AttendanceCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
public class AttendanceCheckController {




    private final AttendanceCheckService attendanceCheckService;

    public AttendanceCheckController(AttendanceCheckService attendanceCheckService) {
        this.attendanceCheckService = attendanceCheckService;
    }


    //id 조회
    @PostMapping("/members/:memberId/attendance-checks/{id}")
    public ResponseEntity<AttendanceCheckDTO> attendanceCheck(@PathVariable Long id) {
        return attendanceCheckService.attendanceCheck(id);
    }


    @GetMapping("/members/{memberId}/attendance-checks/{id}")
    public ResponseEntity<List<CheckedMemberDTO>> getAttendance(
            @PathVariable Long id,
            @RequestParam(name = "year") int year,
            @RequestParam(name = "month") int month) {
        return attendanceCheckService.getAttendance(id, year, month);
    }



}
