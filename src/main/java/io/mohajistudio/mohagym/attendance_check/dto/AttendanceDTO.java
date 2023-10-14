package io.mohajistudio.mohagym.attendance_check.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AttendanceDTO {
    private Long id;
    private LocalDateTime createdAt;

    public AttendanceDTO() {
    }
}

