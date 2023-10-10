package io.mohajistudio.mohagym.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name = "attendance_checks")
public class AttendanceCheck extends BaseEntity {
    @ManyToOne(optional = false)
    private Member member;
}
