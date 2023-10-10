package io.mohajistudio.mohagym.attendance_check.service;

import io.mohajistudio.mohagym.entity.Member;
import io.mohajistudio.mohagym.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public AttendanceService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
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
            response.put("status", "success");
            response.put("created at", LocalDateTime.now());
            return ResponseEntity.ok(response.toString());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
