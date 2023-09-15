package io.mohajistudio.mohagym.provider.service;

import io.mohajistudio.mohagym.configuration.exception.CustomException;
import io.mohajistudio.mohagym.configuration.exception.ErrorCode;
import io.mohajistudio.mohagym.core.security.role.Role;
import io.mohajistudio.mohagym.entity.Member;
import io.mohajistudio.mohagym.provider.security.JwtAuthToken;
import io.mohajistudio.mohagym.provider.security.JwtAuthTokenProvider;
import io.mohajistudio.mohagym.repository.MemberRepository;
import io.mohajistudio.mohagym.util.SHA256Util;
import io.mohajistudio.mohagym.web.dto.RequestMember;
import io.mohajistudio.mohagym.web.dto.ResponseMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    //회원가입 로직
    @Transactional
    public void register(RequestMember.Member requestDto) {
        Member member = memberRepository.findByUserId(requestDto.getUserId());
        if (member != null) { //아이디 중복
            throw new CustomException(ErrorCode.AUTHENTICATION_FAILED);
        }
        //salt 생성
        String salt = SHA256Util.generateSalt();
        //salt랑 비밀번호 암호화
        String encryptedPassword = SHA256Util.getEncrypt(requestDto.getPassword(), salt);

        member = io.mohajistudio.mohagym.entity.Member.builder()
                .userId(requestDto.getUserId())
                .password(encryptedPassword)
                .salt(salt)
                .role(Role.USER.getCode())
                .build();
        memberRepository.save(member);
    }
    @Transactional
    public ResponseMember.Token login(RequestMember.Member requestDto) {
        Member member = memberRepository.findByUserId(requestDto.getUserId());
        if (member == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_ADMIN);
        }
        //솔트 꺼내기
        String salt = member.getSalt();
        member = memberRepository.findByUserIdAndPassword(requestDto.getUserId(),
                SHA256Util.getEncrypt(requestDto.getPassword(), salt));
        if (member == null) {//비밀번호가 틀렸을 경우
            throw new CustomException(ErrorCode.NOT_FOUND_ADMIN);
        }
        //로그인 성공
        String refreshToken = createRefreshToken(member.getUserId(),member.getRole());
        ResponseMember.Token login = ResponseMember.Token.builder()
                .accessToken(createAccessToken(member.getUserId(), member.getRole()))
                .refreshToken(refreshToken)
                .build();
        //refreshToken 업데이트
        member.setRefreshToken(refreshToken);

        return login;
    }
    @Transactional
    public String changeRole(RequestMember.Member requestDto, RequestMember.Member requestDto2 ){
       //서비스를 사용하는 주체의 인가정보 확인(예외처리)
        Member member = memberRepository.findByUserId(requestDto.getUserId());
        if (member == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_ADMIN);
        }
        else if (member.getRole() != Role.ADMIN.getCode()) {
            throw new CustomException(ErrorCode.NOT_AUTHORIZED);
        }
        //바꾸려는 사용자의 정보 조회(역할 조회)
        Member member2 = memberRepository.findByUserId(requestDto2.getUserId());
        if (member2 == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_ADMIN);
        }

        //역할 바꾸는 로직(어드민->일반, 일반->어드민)
        if(member2.getRole() == Role.USER.getCode()){
            member2.setRole(Role.ADMIN.getCode());
        }
        else{
        member2.setRole(Role.USER.getCode());}
        //refreshToken 업데이트
        String refreshToken = createRefreshToken(member2.getUserId(), member.getRole());
        member2.setRefreshToken(refreshToken);
        //바뀐역할 반환
         memberRepository.save(member2);
        return Role.findByCode(member2.getRole()).getDescription();
    }

    public String createAccessToken(String userId,String role) {
        Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(60).atZone(ZoneId.systemDefault()).toInstant()); //현재 시간에서 1시간을 더한 시간을 만들어서 만료 날짜를 설정
        JwtAuthToken accessToken = jwtAuthTokenProvider.createAuthToken(userId, role, expiredDate);
        return accessToken.getToken();
    }

    public String createRefreshToken(String userId, String role) {
        Date expiredDate = Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant()); //현재 시간에서 30일을 더한 시간을 만들어서 만료 날짜를 설정
        JwtAuthToken refreshToken = jwtAuthTokenProvider.createAuthToken(userId, role, expiredDate);
        return refreshToken.getToken();
    }


}
