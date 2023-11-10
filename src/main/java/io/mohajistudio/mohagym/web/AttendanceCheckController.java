package io.mohajistudio.mohagym.web;


import io.mohajistudio.mohagym.web.dto.AttendanceCheckDTO;
import io.mohajistudio.mohagym.web.dto.CheckedMemberDTO;
import io.mohajistudio.mohagym.provider.service.AttendanceCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
//    @PostMapping("/members/attendance-checks/{id}")
//    public ResponseEntity<AttendanceCheckDTO> attendanceCheck(@PathVariable Long id) {
//        return attendanceCheckService.attendanceCheck(id);
//    }
    @PostMapping("/members/attendance-checks/{memberId}/{attendanceCheckId}")
    public ResponseEntity<AttendanceCheckDTO> attendanceCheck(@PathVariable Long memberId, @PathVariable Long attendanceCheckId) {
        return attendanceCheckService.attendanceCheck(memberId, attendanceCheckId);
    }




    @GetMapping("/members/attendance-checks/{id}")
    public ResponseEntity<Page<AttendanceCheckDTO>> getAttendance(
            @PathVariable Long id,
            @RequestParam(name = "year") int year,
            @RequestParam(name = "month") int month,
            Pageable pageable) {
        return attendanceCheckService.getAttendance(id, year, month, pageable);
    }

}
