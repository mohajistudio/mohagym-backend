package io.mohajistudio.mohagym.web.dto;

import lombok.*;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class requestDto {
    protected String email;
    protected String password;

    @Getter
    public static class MemberProfile extends requestDto {
        private String name;
        //  private String profileImage;
        private LocalDate birthday;
        private String phoneNo;
        private String sex;
    }
}




