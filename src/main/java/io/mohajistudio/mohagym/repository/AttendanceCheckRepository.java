package io.mohajistudio.mohagym.repository;

import io.mohajistudio.mohagym.entity.AttendanceCheck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AttendanceCheckRepository extends JpaRepository<AttendanceCheck, Long> {
    Page<AttendanceCheck> findByMemberIdAndCreatedAtBetween(Long member, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
