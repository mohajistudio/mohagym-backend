package io.mohajistudio.mohagym.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AttendanceCheckDTO {
    private Long attendanceCheckedId;
    private LocalDateTime createdAt;

    public AttendanceCheckDTO() {
    }
}

