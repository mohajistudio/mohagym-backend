package io.mohajistudio.mohagym.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "members")
@EqualsAndHashCode(callSuper=false)
public class Member extends BaseEntity {
    private String email;
    private String password;

    @OneToOne(mappedBy = "member")
    private MemberProfile memberProfile;

    @OneToMany(mappedBy = "author")
    private List<Notice> notices = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<AttendanceCheck> attendanceChecks = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ExerciseRecord> exerciseRecords = new ArrayList<>();
}
