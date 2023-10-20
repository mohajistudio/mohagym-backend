package io.mohajistudio.mohagym.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class responseDto {
    @Getter
    public static class Member{
        private Long id;
        private String email;
        private String role;
        private responseDto.MemberProfile MemberProfile;
        @Builder
        public Member(Long id, String email, String role, responseDto.MemberProfile memberProfile) {
            this.id = id;
            this.email = email;
            this.role = role;
            MemberProfile = memberProfile;
        }
    }
    @Getter
    public static class MemberProfile{
        private String name;
        private String phoneNo;

        @Builder
        public MemberProfile(String name, String phoneNo) {
            this.name = name;
            this.phoneNo = phoneNo;
        }
    }



}
