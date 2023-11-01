package io.mohajistudio.mohagym.attendance_check.repository;

import io.mohajistudio.mohagym.entity.AttendanceCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AttendanceCheckRepository extends JpaRepository<AttendanceCheck, Long> {
    List<AttendanceCheck> findByMemberIdAndCreatedAtBetween(Long member, LocalDateTime startDate, LocalDateTime endDate);
}
