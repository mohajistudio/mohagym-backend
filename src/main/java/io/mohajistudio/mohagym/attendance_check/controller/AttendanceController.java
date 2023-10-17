package io.mohajistudio.mohagym.attendance_check.controller;


import io.mohajistudio.mohagym.attendance_check.dto.AttendanceDTO;
import io.mohajistudio.mohagym.attendance_check.service.AttendanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }


    //id 조회
    @PostMapping("/members/:memberId/attendance-checks/{id}")
    public ResponseEntity<AttendanceDTO> checkId(@PathVariable Long id) {
        return attendanceService.checkId(id);
    }


    @GetMapping("/members/{memberId}/attendance-checks/{id}")
    public ResponseEntity<List<AttendanceDTO>> attendanceRecord(
            @PathVariable Long id,
            @RequestParam(name = "year") int year,
            @RequestParam(name = "month") int month) {
        return attendanceService.attendanceRecord(id, year, month);
    }



}
