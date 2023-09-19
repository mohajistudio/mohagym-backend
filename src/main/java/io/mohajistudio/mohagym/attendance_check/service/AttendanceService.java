package io.mohajistudio.mohagym.attendance_check.service;

import io.mohajistudio.mohagym.attendance_check.model.Member;
import io.mohajistudio.mohagym.attendance_check.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AttendanceService {
    @Autowired
    private MemberRepository memberRepository;

    public String checkAttendance(String name) {
        Optional<Member> memberOptional = memberRepository.findByName(name);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            // 출석체크 로그 저장 로직을 추가
            return "출석체크 성공";
        } else {
            return "회원이 존재하지 않습니다.";
        }
    }
}
