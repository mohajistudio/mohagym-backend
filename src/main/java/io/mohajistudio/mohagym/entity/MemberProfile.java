package io.mohajistudio.mohagym.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "member_profiles")
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class MemberProfile extends BaseEntity implements Serializable {
    public static final long serialVersionUID = 1234L;
    @JsonIgnore // id 필드를 JSON 출력에서 제외
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
   // private String profileImage;

    private LocalDate birthday;
    private String phoneNo;

    private String sex;

    @JsonIgnore
    @OneToOne(optional = false)
    @ToString.Exclude
    private Member member;

    @Builder
    public MemberProfile(String name, LocalDate birthday, String phoneNo, String sex, Member member) {
        this.name = name;
        //this.profileImage = profileImage;
        this.birthday = birthday;
        this.phoneNo = phoneNo;
        this.sex = sex;
        this.member = member;
    }
    // 직접 필드에 접근하여 수정
    public void updatePhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    public void updateName(String name) {
        this.name = name;
    }
}
