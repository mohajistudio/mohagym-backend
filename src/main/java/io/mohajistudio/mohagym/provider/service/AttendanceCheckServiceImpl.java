package io.mohajistudio.mohagym.provider.service;

import io.mohajistudio.mohagym.web.dto.AttendanceCheckDTO;
import io.mohajistudio.mohagym.repository.AttendanceCheckRepository;
import io.mohajistudio.mohagym.entity.AttendanceCheck;
import io.mohajistudio.mohagym.entity.Member;
import io.mohajistudio.mohagym.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class AttendanceCheckServiceImpl implements AttendanceCheckService{
    private final MemberRepository memberRepository;
    private final AttendanceCheckRepository attendanceCheckRepository;

    public AttendanceCheckServiceImpl(MemberRepository memberRepository, AttendanceCheckRepository attendanceCheckRepository) {
        this.memberRepository = memberRepository;
        this.attendanceCheckRepository = attendanceCheckRepository;
    }

        public ResponseEntity<AttendanceCheckDTO> attendanceCheck(Long memberId) {
        Optional<Member> memberEntity = memberRepository.findById(memberId);
        Map<String, Object> response = new HashMap<>();
        if (memberEntity.isPresent()) {
            Member member = memberEntity.get();

            AttendanceCheck attendanceCheck = new AttendanceCheck();
            attendanceCheck.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
            attendanceCheck.setMember(member);

            AttendanceCheck savedAttendanceCheckTime = attendanceCheckRepository.save(attendanceCheck);

            AttendanceCheckDTO attendanceCheckDTO = new AttendanceCheckDTO();
            attendanceCheckDTO.setAttendanceCheckedId(attendanceCheck.getId());
            attendanceCheckDTO.setCreatedAt(savedAttendanceCheckTime.getCreatedAt());

            response.put("status", "success");
            response.put("created at", savedAttendanceCheckTime.getCreatedAt());

            return ResponseEntity.ok(attendanceCheckDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @Override
    public ResponseEntity<Page<AttendanceCheckDTO>> getAttendance(Long memberId, int year, int month, Pageable pageable) {
        Optional<Member> memberEntity = memberRepository.findById(memberId);
        if (memberEntity.isPresent()) {
            LocalDateTime startDateTime = LocalDateTime.of(year, month, 1, 0, 0);
            LocalDateTime endDateTime = startDateTime.plusMonths(1).minusNanos(1);

            Page<AttendanceCheck> attendanceCheckTimes = attendanceCheckRepository.findByMemberIdAndCreatedAtBetween(memberId, startDateTime, endDateTime, pageable);

            Page<AttendanceCheckDTO> attendanceCheckDTOPage = attendanceCheckTimes.map(attendanceCheckTime -> {
                AttendanceCheckDTO attendanceCheckDTO = new AttendanceCheckDTO();
                attendanceCheckDTO.setAttendanceCheckedId(attendanceCheckTime.getId());
                attendanceCheckDTO.setCreatedAt(attendanceCheckTime.getCreatedAt());
                return attendanceCheckDTO;
            });

            return ResponseEntity.ok(attendanceCheckDTOPage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}