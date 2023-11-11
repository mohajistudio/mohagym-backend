package io.mohajistudio.mohagym.web;


import io.mohajistudio.mohagym.web.dto.AttendanceCheckDTO;
import io.mohajistudio.mohagym.provider.service.AttendanceCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/members/{memberId}/attendance-checks")
public class AttendanceCheckController {

    private final AttendanceCheckService attendanceCheckService;


    public AttendanceCheckController(AttendanceCheckService attendanceCheckService) {
        this.attendanceCheckService = attendanceCheckService;
    }


    @PostMapping
    public ResponseEntity<AttendanceCheckDTO> attendanceCheck(@PathVariable Long memberId) {
        return attendanceCheckService.attendanceCheck(memberId);
    }




    @GetMapping
    public ResponseEntity<Page<AttendanceCheckDTO>> getAttendance(
            @PathVariable Long memberId,
            @RequestParam(name = "year") int year,
            @RequestParam(name = "month") int month,
            Pageable pageable) {
        return attendanceCheckService.getAttendance(memberId, year, month, pageable);
    }

}
