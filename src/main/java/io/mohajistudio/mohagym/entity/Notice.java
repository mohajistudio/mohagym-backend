package io.mohajistudio.mohagym.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "notices")
@EqualsAndHashCode(callSuper = false)
public class Notice extends BaseEntity {
<<<<<<< HEAD
    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "writer")
    private String writer;

    @Column(name = "view_cnt")
    private Integer viewCnt;
=======
    private String title;
    private String body;
>>>>>>> 48f6ce92ae29b6579ed4ebfd551ab897cb111072

    @ManyToOne(optional = false)
    private Member author;
}
<<<<<<< HEAD

=======
>>>>>>> 48f6ce92ae29b6579ed4ebfd551ab897cb111072
