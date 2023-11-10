package io.mohajistudio.mohagym.provider.service;

import io.mohajistudio.mohagym.web.dto.AttendanceCheckDTO;
import io.mohajistudio.mohagym.web.dto.CheckedMemberDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AttendanceCheckService {
    ResponseEntity<AttendanceCheckDTO> attendanceCheck(Long id, Long attendanceCheckId);

    ResponseEntity<Page<AttendanceCheckDTO>> getAttendance(Long id, int year, int month, Pageable pageable);
}
