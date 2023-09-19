package io.mohajistudio.mohagym.attendance_check.service;

import io.mohajistudio.mohagym.attendance_check.model.Member;
import io.mohajistudio.mohagym.attendance_check.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public boolean checkAttendance(String name) {
        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            if (member.getName().equals(name)) {
                // 출석체크 로그 저장 로직 추가
                return true;
            }
        }
        return false;
    }
}
