package io.mohajistudio.mohagym.attendance_check.dto;

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
