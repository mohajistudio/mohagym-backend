package io.mohajistudio.mohagym.provider.service;


import io.mohajistudio.mohagym.web.dto.responseMember;
import io.mohajistudio.mohagym.web.dto.requestMember;
import io.mohajistudio.mohagym.web.dto.requestUserId;
import jakarta.servlet.http.HttpServletRequest;


public interface MemberService {
    void register(requestMember requestDto);
    responseMember login(requestMember requestDto);
    String changeRole(requestUserId requestDto );

    void logout(HttpServletRequest request);
    responseMember reissueToken(HttpServletRequest request);
}
