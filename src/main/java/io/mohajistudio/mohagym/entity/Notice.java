package io.mohajistudio.mohagym.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "notices")
@EqualsAndHashCode(callSuper = false)
public class Notice extends BaseEntity {
    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "writer")
    private String writer;

    @Column(name = "view_cnt")
    private Integer viewCnt;

    @ManyToOne(optional = false)
    private Member author;
}
