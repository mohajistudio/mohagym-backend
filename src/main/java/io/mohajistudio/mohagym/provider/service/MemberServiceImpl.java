package io.mohajistudio.mohagym.provider.service;

import io.mohajistudio.mohagym.configuration.exception.CustomException;
import io.mohajistudio.mohagym.configuration.exception.ErrorCode;
import io.mohajistudio.mohagym.core.security.role.Role;
import io.mohajistudio.mohagym.entity.Member;
import io.mohajistudio.mohagym.provider.security.JwtAuthToken;
import io.mohajistudio.mohagym.provider.security.JwtAuthTokenProvider;
import io.mohajistudio.mohagym.repository.MemberRepository;
import io.mohajistudio.mohagym.util.SHA256Util;
import io.mohajistudio.mohagym.web.dto.responseMember;
import io.mohajistudio.mohagym.web.dto.requestMember;
import io.mohajistudio.mohagym.web.dto.requestUserId;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;



@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService  {

    private final MemberRepository memberRepository;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;


    //회원가입 로직
    @Transactional
    @Override
    public void register(requestMember requestDto) {
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
    @Override
    public responseMember login(requestMember requestDto) {
        Member member = memberRepository.findByUserId(requestDto.getUserId());
        if (member == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        //솔트 꺼내기
        String salt = member.getSalt();
        member = memberRepository.findByUserIdAndPassword(requestDto.getUserId(),
                SHA256Util.getEncrypt(requestDto.getPassword(), salt));
        if (member == null) {//비밀번호가 틀렸을 경우
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        //로그인 성공
        String refreshToken = createRefreshToken(member.getUserId(),member.getRole());
        responseMember login = responseMember.builder()
                .accessToken(createAccessToken(member.getUserId(), member.getRole()))
                .build();
        //refreshToken 업데이트
        member.setRefreshToken(refreshToken);

        return login;
    }
    @Transactional
    @Override
    public String changeRole( requestUserId requestDto ){

        //바꾸려는 사용자의 정보 조회(역할 조회)
        Member member = memberRepository.findByUserId(requestDto.getUserId());
        if (member == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }

        //역할 바꾸는 로직(어드민->일반, 일반->어드민)
        if(member.getRole() == Role.USER.getCode()){
            member.setRole(Role.ADMIN.getCode());
        }
        else{
        member.setRole(Role.USER.getCode());}
        //refreshToken 업데이트//권한을 바꾸려는 유저가 로그인 되어있을경우만
        if(member.getRefreshToken() != null){
        String refreshToken = createRefreshToken(member.getUserId(), member.getRole());
        member.setRefreshToken(refreshToken);}
        //바뀐역할 반환
         memberRepository.save(member);
        return Role.findByCode(member.getRole()).getDescription();
    }
    //로그아웃 로직
    @Transactional
    @Override
    public void logout(HttpServletRequest request){
        String token = jwtAuthTokenProvider.getAuthToken(request).get();
        Member member = memberRepository.findByUserId(jwtAuthTokenProvider.getUserIdFromToken(token));
        if (member == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        member.setRefreshToken(null);
        memberRepository.save(member);
    }

    //토큰 재발급//엑세스 토큰이 만료되었을 때
    @Transactional
    @Override
    public responseMember reissueToken(HttpServletRequest request){

        String token = jwtAuthTokenProvider.getAuthToken(request).get();
        Member member = memberRepository.findByUserId(jwtAuthTokenProvider.getUserIdFromToken(token));

       String refreshToken = member.getRefreshToken();
        JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(refreshToken);
       if(refreshToken==null){
           return null;
       }

       else if(jwtAuthToken.isNotExpired()&&jwtAuthToken.validate()){
           //accessToken 재발급
           responseMember  reissue = responseMember.builder()
                   .accessToken(createAccessToken(member.getUserId(), member.getRole()))
                   .build();
           //refreshToken 업데이트//RTR방식
           refreshToken = createRefreshToken(member.getUserId(),member.getRole());
           member.setRefreshToken(refreshToken);

           return reissue;
            }
       logout(request);
        return null;

    }
    private String createAccessToken(String userId,String role) {
        Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(1).atZone(ZoneId.systemDefault()).toInstant()); //현재 시간에서 1시간을 더한 시간을 만들어서 만료 날짜를 설정
        JwtAuthToken accessToken = jwtAuthTokenProvider.createAuthToken(userId, role, expiredDate);
        return accessToken.getToken();
    }

    private String createRefreshToken(String userId, String role) {
        Date expiredDate = Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant()); //현재 시간에서 30일을 더한 시간을 만들어서 만료 날짜를 설정
        JwtAuthToken refreshToken = jwtAuthTokenProvider.createAuthToken(userId, role, expiredDate);
        return refreshToken.getToken();
    }

}
