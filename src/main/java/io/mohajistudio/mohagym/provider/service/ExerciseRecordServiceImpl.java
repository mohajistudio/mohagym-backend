package io.mohajistudio.mohagym.provider.service;

import io.mohajistudio.mohagym.entity.ExerciseRecord;
import io.mohajistudio.mohagym.entity.Member;
import io.mohajistudio.mohagym.repository.ExerciseRecordRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExerciseRecordServiceImpl implements ExerciseRecordService {
    private final ExerciseRecordRepositoryImpl exerciseRecordRepository;

    @Override
    public ExerciseRecord findExerciseRecord(Integer memberId, Integer erId) {
        Optional<ExerciseRecord> result = exerciseRecordRepository.findById(erId.longValue());

        if (result.isPresent()) {
            ExerciseRecord exerciseRecord = result.get();
            if (exerciseRecord.getMember().getId() != memberId.longValue()) {
                throw new RuntimeException("400 BAD REQUEST");
            }
            return exerciseRecord;
        }

        throw new RuntimeException("404 NOT FOUND");
    }

    @Override
    public Page<ExerciseRecord> findExerciseRecordList(Integer memberId, Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        return exerciseRecordRepository.findAllByMemberId(memberId.longValue(), pageable);
    }

    @Override
    public ExerciseRecord addExerciseRecord(Integer memberId, String memo) {
        ExerciseRecord exerciseRecord = new ExerciseRecord();
        exerciseRecord.setMemo(memo);

        Member member = new Member();
        member.setId(memberId.longValue());
        exerciseRecord.setMember(member);

        return exerciseRecordRepository.save(exerciseRecord);
    }

    @Override
    public ExerciseRecord modifyExerciseRecord(Integer memberId, Integer erId, String memo) {
        Optional<ExerciseRecord> result = exerciseRecordRepository.findById(erId.longValue());

        if (result.isPresent()) {
            ExerciseRecord exerciseRecord = result.get();

            if (exerciseRecord.getMember().getId() != memberId.longValue()) {
                throw new RuntimeException("400 BAD REQUEST");
            }

            exerciseRecord.setMemo(memo);

            exerciseRecordRepository.save(exerciseRecord);
            return exerciseRecord;
        }

        throw new RuntimeException("404 NOT FOUND");
    }

    @Override
    public void removeExerciseRecord(Integer memberId, Integer erId) {
        Optional<ExerciseRecord> result = exerciseRecordRepository.findById(erId.longValue());

        if (result.isPresent()) {
            ExerciseRecord exerciseRecord = result.get();

            if (exerciseRecord.getMember().getId() != memberId.longValue()) {
                throw new RuntimeException("400 BAD REQUEST");
            }

            exerciseRecordRepository.delete(exerciseRecord);
        } else {
            throw new RuntimeException("404 NOT FOUND");
        }
    }
}
