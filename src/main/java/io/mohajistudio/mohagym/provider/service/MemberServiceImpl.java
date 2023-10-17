package io.mohajistudio.mohagym.provider.service;

import io.mohajistudio.mohagym.configuration.exception.CustomException;
import io.mohajistudio.mohagym.configuration.exception.ErrorCode;
import io.mohajistudio.mohagym.core.security.role.Role;
import io.mohajistudio.mohagym.entity.Member;
import io.mohajistudio.mohagym.entity.MemberProfile;
import io.mohajistudio.mohagym.provider.security.JwtAuthToken;
import io.mohajistudio.mohagym.provider.security.JwtAuthTokenProvider;
import io.mohajistudio.mohagym.repository.MemberProfileRepository;
import io.mohajistudio.mohagym.repository.MemberRepository;
import io.mohajistudio.mohagym.util.SHA256Util;
import io.mohajistudio.mohagym.web.dto.requestDto;
import io.mohajistudio.mohagym.web.dto.requestToken;
import io.mohajistudio.mohagym.web.dto.responseMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final MemberProfileRepository memberProfileRepository;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;


    //회원가입 로직
    @Transactional
    @Override
    public void register(requestDto.requestMemberProfile requestDto) {
        Member member = memberRepository.findByEmailAndDeletedAtIsNull(requestDto.getEmail());
        if (member != null) { //아이디 중복
            throw new CustomException(ErrorCode.AUTHENTICATION_CONFLICT);
        }
        //salt 생성
        String salt = SHA256Util.generateSalt();
        //salt랑 비밀번호 암호화
        String encryptedPassword = SHA256Util.getEncrypt(requestDto.getPassword(), salt);

        member = io.mohajistudio.mohagym.entity.Member.builder()
                .email(requestDto.getEmail())
                .password(encryptedPassword)
                .salt(salt)
                .role(Role.USER.getCode())
                .build();
        memberRepository.save(member);

        MemberProfile memberProfile = MemberProfile.builder()
                .name(requestDto.getName())
               // .profileImage(requestDto.getProfileImage())
                .birthday(requestDto.getBirthday())
                .phoneNo(requestDto.getPhoneNo())
                .sex(requestDto.getSex())
                .member(member).build();

        memberProfileRepository.save(memberProfile);
    }
    @Transactional
    @Override
    public responseMember login(requestDto requestDto) {
        Member member = memberRepository.findByEmailAndDeletedAtIsNull(requestDto.getEmail());
        if (member == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        //솔트 꺼내기
        String salt = member.getSalt();
        member = memberRepository.findByEmailAndPasswordAndDeletedAtIsNull(requestDto.getEmail(),
                SHA256Util.getEncrypt(requestDto.getPassword(), salt));
        if (member == null) {//비밀번호가 틀렸을 경우
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        //로그인 성공
        String refreshToken = createRefreshToken(member.getEmail(),member.getRole());
        responseMember login = responseMember.builder()
                .accessToken(createAccessToken(member.getEmail(), member.getRole()))
                .refreshToken(refreshToken)
                .build();
        //refreshToken 업데이트
        member.setRefreshToken(refreshToken);

        return login;
    }
    @Transactional
    @Override
    public String changeRole(String name){

        //바꾸려는 사용자의 정보 조회(역할 조회)
        Member member = memberProfileRepository.findByNameAndDeletedAtIsNull(name).getMember();

        if (member == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }

        //역할 바꾸는 로직(어드민->일반, 일반->어드민)
        if(member.getRole().equals( Role.USER.getCode())){
            member.setRole(Role.ADMIN.getCode());
        }
        else{
        member.setRole(Role.USER.getCode());}
        //refreshToken 업데이트//권한을 바꾸려는 유저가 로그인 되어있을경우만
        if(member.getRefreshToken() != null){
        String refreshToken = createRefreshToken(member.getEmail(), member.getRole());
        member.setRefreshToken(refreshToken);}
        //바뀐역할 반환
         memberRepository.save(member);
        return Role.findByCode(member.getRole()).getDescription();
    }
    //로그아웃 로직//dto를 사용하도록 변경
    @Transactional
    @Override
    public void logout(requestToken token){

        Member member = memberRepository.findByEmailAndDeletedAtIsNull(jwtAuthTokenProvider.getUserIdFromToken(token.getAccessToken()));
        if (member == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        member.setRefreshToken(null);
        memberRepository.save(member);
    }

    //토큰 재발급//엑세스 토큰이 만료되었을 때
    //참고 블로그
    //https://velog.io/@myway00/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EB%A6%AC%EC%95%A1%ED%8A%B8-jwt-%ED%86%A0%ED%81%B0-%EC%84%A4%EC%A0%95%EB%B0%A9%EB%B2%95
    @Transactional
    @Override
    public responseMember reissueToken(requestToken oldTokens){
        Member member = memberRepository.findByRefreshTokenAndDeletedAtIsNull(oldTokens.getRefreshToken());

        if(jwtAuthTokenProvider.convertAuthToken(oldTokens.getRefreshToken()).validate()){//refresh토큰 유효성 조사
        //refreshToken 업데이트//RTR방식
        String refreshToken = createRefreshToken(member.getEmail(),member.getRole());
        //accessToken 재발급
        responseMember  newTokens = responseMember.builder()
                .accessToken(createAccessToken(member.getEmail(), member.getRole()))
                .refreshToken(refreshToken)
                .build();

        member.setRefreshToken(refreshToken);
        memberRepository.save(member);


        return newTokens;
        }
        //refresh토큰 이 유효하지 않으면 강제 로그아웃
        member.setRefreshToken(null);
        memberRepository.save(member);
        throw new CustomException(ErrorCode.AUTHENTICATION_FAILED);

    }

    private String createAccessToken(String userId,String role) {
        Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(60).atZone(ZoneId.systemDefault()).toInstant()); //현재 시간에서 1시간을 더한 시간을 만들어서 만료 날짜를 설정
        JwtAuthToken accessToken = jwtAuthTokenProvider.createAuthToken(userId, role, expiredDate);
        return accessToken.getToken();
    }

    private String createRefreshToken(String userId, String role) {
        Date expiredDate = Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant()); //현재 시간에서 30일을 더한 시간을 만들어서 만료 날짜를 설정
        JwtAuthToken refreshToken = jwtAuthTokenProvider.createAuthToken(userId, role, expiredDate);
        return refreshToken.getToken();
    }

    @Transactional
    @Override
    public Page<Member> getAllMembers(int page, int size){
        Sort sort = Sort.by(Sort.Direction.ASC, "id"); // id 필드를 오름차순으로 정렬
        Pageable pageable = PageRequest.of(page,size,sort);// 페이지 및 사이즈와 정렬 조건을 함께 설정
        return memberRepository.findAllByDeletedAtIsNull(pageable);

    }
    //이름으로 회원 검색
    @Transactional
    @Override
    public Page<Member> getMemberByName(int page, int size, String name){
        Sort sort = Sort.by(Sort.Direction.ASC, "id"); // id 필드를 오름차순으로 정렬
        Pageable pageable = PageRequest.of(page,size,sort);// 페이지 및 사이즈와 정렬 조건을 함께 설정
        return memberRepository.findByMemberProfileNameContainingAndDeletedAtIsNull(name, pageable);
    }
    //멤버 아이디로 멤버 프로필 가져와서 보여주기
    @Transactional
    @Override
    public MemberProfile getMemberProfileById(Long Id){

        return memberProfileRepository.findByIdAndDeletedAtIsNull(Id);
    }
    //회원 탈퇴//멤버엔티티와 멤버프로필 엔티티의 base엔티티의 deletedAt에 현재 시간을 찍어야 함
    @Transactional
    @Override
    public void disableMember(Long Id){
        Member member = memberRepository.findByIdAndDeletedAtIsNull(Id);
        if (member.getDeletedAt() == null) {
            member.setDeletedAt(LocalDateTime.now());
            memberRepository.save(member);
        }else {
            throw new CustomException(ErrorCode.Already_Deleted);
        }

        MemberProfile memberProfile = memberProfileRepository.findByIdAndDeletedAtIsNull(Id);
        if (memberProfile.getDeletedAt() == null) {
        memberProfile.setDeletedAt(LocalDateTime.now());
        memberProfileRepository.save(memberProfile);}
        else{
            throw new CustomException(ErrorCode.Already_Deleted);
        }
    }
}
