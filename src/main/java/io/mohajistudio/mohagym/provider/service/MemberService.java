package io.mohajistudio.mohagym.provider.service;


import io.mohajistudio.mohagym.entity.MemberProfile;
import io.mohajistudio.mohagym.web.dto.*;
import org.springframework.data.domain.Page;


public interface MemberService {
    //회원가입
    void register(requestDto.MemberProfile requestDto);
    //로그인
    responseToken login(requestDto requestDto);

    //로그아웃
    void logout(requestToken token);
    //토큰 재발급
    responseToken reissueToken(requestToken oldTokens);
    //회원조회 // 멤버의 이름,전화번호 멤버의 아이디 같이 냐려 주어야 함
    Page<responseDto.Member> getAllMembers(int page, int size);
    //이름으로 회원 검색
    Page<responseDto.Member> getMemberByName(int page, int size, String name);

    //멤버 아이디로 멤버 프로필 가져와서 보여주기
     MemberProfile getMemberProfileById(Long Id);
    //회원 탈퇴

    void disableMember(Long Id);

    //회원 정보 수정
    //롤 체인지
    String changeRole(String name);
    //폰 번호 변경
    void changeName(requestProfile.name dto);

    void changePhoneNo(requestProfile.phoneNo dto);
}
