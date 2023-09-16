package io.mohajistudio.mohagym.provider.service;


import io.mohajistudio.mohagym.web.dto.ResponseMember;
import io.mohajistudio.mohagym.web.dto.requestMember;
import io.mohajistudio.mohagym.web.dto.requestUserId;


public interface MemberService {
    void register(requestMember requestDto);
    ResponseMember.Token login(requestMember requestDto);
    String changeRole(requestUserId requestDto );

}