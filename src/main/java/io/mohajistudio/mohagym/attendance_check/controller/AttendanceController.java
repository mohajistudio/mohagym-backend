package io.mohajistudio.mohagym.attendance_check.controller;


import io.mohajistudio.mohagym.attendance_check.service.AttendanceServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
public class AttendanceController {

    private final AttendanceServiceInterface attendanceServiceInterface;

    public AttendanceController(AttendanceServiceInterface attendanceServiceInterface) {
        this.attendanceServiceInterface = attendanceServiceInterface;
    }



    //id 조회
    @PostMapping("/members/check/{id}")
    public ResponseEntity<String> checkId(@PathVariable Long id) {
        return attendanceServiceInterface.checkId(id);
    }




}
