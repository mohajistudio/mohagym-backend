package io.mohajistudio.mohagym.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "member_profiles")
@EqualsAndHashCode(callSuper=false)
public class MemberProfile extends BaseEntity {
    private String name;
    private String profileImage;
    private LocalDate birthday;
    private String phoneNo;
    private String sex;

    @OneToOne(optional = false)
    @ToString.Exclude
    private Member member;
}
