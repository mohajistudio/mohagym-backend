package io.mohajistudio.mohagym.attendance_check.service;

import io.mohajistudio.mohagym.entity.AttendanceCheckTime;
import io.mohajistudio.mohagym.entity.Member;
import io.mohajistudio.mohagym.attendance_check.repository.AttendanceCheckTimeRepository;
import io.mohajistudio.mohagym.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class AttendanceService implements AttendanceServiceInterface {

    private final MemberRepository memberRepository;
    private final AttendanceCheckTimeRepository attendanceCheckTimeRepository;


    public AttendanceService(MemberRepository memberRepository, AttendanceCheckTimeRepository attendanceCheckTimeRepository) {
        this.memberRepository = memberRepository;
        this.attendanceCheckTimeRepository = attendanceCheckTimeRepository;
    }

    @Override
    public LocalDateTime getCurrentTime(){
        return LocalDateTime.now();
    }



    @ResponseBody
    public ResponseEntity<String> checkId(Long id) {
        Optional<Member> memberEntity = memberRepository.findById(id);
        Map<String, Object> response = new HashMap<>();
        if (memberEntity.isPresent()) {
            Member member = memberEntity.get();

            AttendanceCheckTime attendanceCheckTime = new AttendanceCheckTime();
            attendanceCheckTime.setCreatedAt(LocalDateTime.now());

            // AttendanceCheckTime 엔티티를 저장
            attendanceCheckTimeRepository.save(attendanceCheckTime);

            response.put("status", "success");
            response.put("created at", attendanceCheckTime.getCreatedAt());

            return ResponseEntity.ok(response.toString());
        } else {
            // 회원이 존재하지 않는 경우
            return ResponseEntity.notFound().build();
        }
    }


}
