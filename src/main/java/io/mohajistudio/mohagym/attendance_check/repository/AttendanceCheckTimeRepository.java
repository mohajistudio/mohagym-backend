package io.mohajistudio.mohagym.attendance_check.repository;

import io.mohajistudio.mohagym.entity.AttendanceCheckTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AttendanceCheckTimeRepository extends JpaRepository<AttendanceCheckTime, Long> {
    List<AttendanceCheckTime> findByMemberIdAndCreatedAtBetween(Long member, LocalDateTime startDate, LocalDateTime endDate);
}
