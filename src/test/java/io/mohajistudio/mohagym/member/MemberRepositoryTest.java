package io.mohajistudio.mohagym.member;

import io.mohajistudio.mohagym.entity.Member;
import io.mohajistudio.mohagym.entity.MemberProfile;
import io.mohajistudio.mohagym.repository.MemberProfileRepository;
import io.mohajistudio.mohagym.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberProfileRepository memberProfileRepository;

    @Test
    @Transactional
    void save(){
        String email = "mohajistudio@gmail.com";

        Member member = new Member();
        member.setEmail(email);
        member.setPassword("mohaji2022!");

        Member savedMember = this.memberRepository.save(member);
        Assertions.assertEquals(member.getEmail(), email);

        System.out.println("savedMember = " + savedMember);
    }

    @Test
    @Transactional
    void createMemberAndProfile() {
        String email = "mohajistudio@gmail.com";
        String name = "한창희";

        Member member = new Member();
        member.setEmail(email);
        member.setPassword("mohaji2022!");

        MemberProfile memberProfile = new MemberProfile();
        memberProfile.setProfileImage("프로필 이미지");
        LocalDate birthday = LocalDate.of(1999, 1, 7);
        memberProfile.setBirthday(birthday);
        memberProfile.setName(name);
        memberProfile.setSex("남성");
        memberProfile.setPhoneNo("010-3858-3682");
        memberProfile.setMember(member);

        this.memberRepository.save(member);
        this.memberProfileRepository.save(memberProfile);

        System.out.println("savedMember = " + member);
        System.out.println("savedMemberProfile = " + memberProfile);
    }
}