package io.mohajistudio.mohagym.provider.service;

import io.mohajistudio.mohagym.web.dto.AttendanceCheckDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface AttendanceCheckService {
    ResponseEntity<AttendanceCheckDTO> attendanceCheck(Long memberId);

    ResponseEntity<Page<AttendanceCheckDTO>> getAttendance(Long id, int year, int month, Pageable pageable);
}
