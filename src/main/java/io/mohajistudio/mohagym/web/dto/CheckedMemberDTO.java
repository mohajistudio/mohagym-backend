package io.mohajistudio.mohagym.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CheckedMemberDTO {
    private Long memberId;
    private LocalDateTime createdAt;

    public CheckedMemberDTO() {
    }
}

