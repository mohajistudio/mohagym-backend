package io.mohajistudio.mohagym.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@Data
@NoArgsConstructor
public class requestDto {
    protected String email;
    protected String password;

    public requestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }


    @Getter
    public static class MemberProfile extends requestDto{
        private String name;
      //  private String profileImage;
        private LocalDate birthday;
        private String phoneNo;
        private String sex;

        @Builder
        public MemberProfile(String email, String password, String name, LocalDate birthday, String phoneNo, String sex) {
            super(email, password);
            this.name = name;
            this.birthday = birthday;
            this.phoneNo = phoneNo;
            this.sex = sex;
        }
    }


}




