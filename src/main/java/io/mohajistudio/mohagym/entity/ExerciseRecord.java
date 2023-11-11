package io.mohajistudio.mohagym.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name = "exercise_records")
public class ExerciseRecord extends BaseEntity {
    private String memo;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    private Member member;
}
