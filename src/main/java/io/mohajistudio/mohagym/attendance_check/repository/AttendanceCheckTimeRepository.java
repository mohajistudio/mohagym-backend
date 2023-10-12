package io.mohajistudio.mohagym.attendance_check.repository;

import io.mohajistudio.mohagym.entity.AttendanceCheckTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AttendanceCheckTimeRepository extends JpaRepository<AttendanceCheckTime, Long> {
    AttendanceCheckTime findByCreatedAt(LocalDateTime createdAt);
}
