package io.mohajistudio.mohagym.provider.service;

import io.mohajistudio.mohagym.entity.ExerciseRecord;
import org.springframework.data.domain.Page;

public interface ExerciseRecordService {
    ExerciseRecord findExerciseRecord(Integer memberId, Integer erId);
    Page<ExerciseRecord> findExerciseRecordList(Integer memberId, Integer page, Integer size);
    ExerciseRecord addExerciseRecord(Integer memberId, String memo);
    ExerciseRecord modifyExerciseRecord(Integer memberId, Integer erId, String memo);
    void removeExerciseRecord(Integer memberId, Integer erId);
}
