package io.mohajistudio.mohagym.provider.service;

import io.mohajistudio.mohagym.web.dto.AttendanceCheckDTO;
import io.mohajistudio.mohagym.web.dto.CheckedMemberDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AttendanceCheckService {
    ResponseEntity<AttendanceCheckDTO> attendanceCheck(Long id);

    ResponseEntity<List<CheckedMemberDTO>> getAttendance(Long id, int year, int month);
}
