package io.mohajistudio.mohagym.repository;

import io.mohajistudio.mohagym.entity.ExerciseRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRecordRepositoryImpl extends JpaRepository<ExerciseRecord, Long> {
    Page<ExerciseRecord> findAllByMemberId(Long memberId, Pageable pageable);
}
