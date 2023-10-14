package io.mohajistudio.mohagym.attendance_check.service;

import io.mohajistudio.mohagym.attendance_check.dto.AttendanceDTO;
import io.mohajistudio.mohagym.attendance_check.repository.AttendanceCheckTimeRepository;
import io.mohajistudio.mohagym.entity.AttendanceCheckTime;
import io.mohajistudio.mohagym.entity.Member;
import io.mohajistudio.mohagym.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AttendanceService {
    private final MemberRepository memberRepository;
    private final AttendanceCheckTimeRepository attendanceCheckTimeRepository;

    public AttendanceService(MemberRepository memberRepository, AttendanceCheckTimeRepository attendanceCheckTimeRepository) {
        this.memberRepository = memberRepository;
        this.attendanceCheckTimeRepository = attendanceCheckTimeRepository;
    }

    public ResponseEntity<AttendanceDTO> checkId(Long id) {
        Optional<Member> memberEntity = memberRepository.findById(id);
        Map<String, Object> response = new HashMap<>();
        if (memberEntity.isPresent()) {
            Member member = memberEntity.get();

            AttendanceCheckTime attendanceCheckTime = new AttendanceCheckTime();
            attendanceCheckTime.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
            attendanceCheckTime.setMember(member);

            AttendanceCheckTime savedAttendanceCheckTime = attendanceCheckTimeRepository.save(attendanceCheckTime);

            AttendanceDTO attendanceDTO = new AttendanceDTO();
            attendanceDTO.setId(savedAttendanceCheckTime.getId());
            attendanceDTO.setCreatedAt(savedAttendanceCheckTime.getCreatedAt());

            response.put("status", "success");
            response.put("created at", savedAttendanceCheckTime.getCreatedAt());

            return ResponseEntity.ok(attendanceDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseBody
    public ResponseEntity<String> attendanceRecord(Long id) {
        return null;
    }
}
