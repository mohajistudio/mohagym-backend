package io.mohajistudio.mohagym.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@Table(name = "members")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Member extends BaseEntity implements Serializable {
    public static final long serialVersionUID = 1234L;


    private String email;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String salt;
    @JsonIgnore
    private String role;
    @JsonIgnore
    private String refreshToken;


    @OneToOne(mappedBy = "member")
    private  MemberProfile memberProfile;
    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private List<Notice> notices = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private  List<AttendanceCheck> attendanceChecks = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<ExerciseRecord> exerciseRecords = new ArrayList<>();

    @Builder
    public Member(String email, String password, String role, String salt) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.salt = salt;
    }
    //역할 변경
    public void updateRole(String role) {
        this.role = role;
    }
    //리프레시토큰 변경
    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }


}
