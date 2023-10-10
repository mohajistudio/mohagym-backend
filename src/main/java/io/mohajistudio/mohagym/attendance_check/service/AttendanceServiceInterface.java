package io.mohajistudio.mohagym.attendance_check.service;

import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public interface AttendanceServiceInterface {
    LocalDateTime getCurrentTime();

    ResponseEntity<String> checkId(Long id);

}
