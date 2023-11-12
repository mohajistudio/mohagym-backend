package io.mohajistudio.mohagym.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name = "exercise_records")
public class ExerciseRecord extends BaseEntity {
    private String memo;

    @ManyToOne(optional = false)
    private Member member;
}
