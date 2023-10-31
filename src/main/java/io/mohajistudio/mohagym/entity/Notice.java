package io.mohajistudio.mohagym.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "notices")
@EqualsAndHashCode(callSuper = false)
public class Notice extends BaseEntity {
    private String title;
    private String body;

    @ManyToOne(optional = false)
    private Member author;
}
