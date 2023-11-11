package io.mohajistudio.mohagym.web;

import io.mohajistudio.mohagym.entity.ExerciseRecord;
import io.mohajistudio.mohagym.provider.service.ExerciseRecordService;
import io.mohajistudio.mohagym.web.dto.ExerciseRecordDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/members/{memberId}/exercise-records")
public class ExerciseRecordController {
    private final ExerciseRecordService exerciseRecordService;

    @GetMapping
    public ResponseEntity<Page<ExerciseRecord>> exerciseRecordList(@PathVariable Integer memberId, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "20") Integer size) {
        Page<ExerciseRecord> exerciseRecordList = exerciseRecordService.findExerciseRecordList(memberId, page, size);

        return new ResponseEntity<>(exerciseRecordList, HttpStatus.OK);
    }

    @GetMapping("/{er-id}")
    public ResponseEntity<ExerciseRecord> exerciseRecordDetail(@PathVariable Integer memberId, @PathVariable("er-id") Integer erId) {
        ExerciseRecord exerciseRecord = exerciseRecordService.findExerciseRecord(memberId, erId);

        return new ResponseEntity<>(exerciseRecord, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ExerciseRecord> exerciseRecordAdd(@PathVariable Integer memberId, @RequestBody ExerciseRecordDto exerciseRecordDto) {
        ExerciseRecord exerciseRecord = exerciseRecordService.addExerciseRecord(memberId, exerciseRecordDto.getMemo());

        return new ResponseEntity<>(exerciseRecord, HttpStatus.OK);
    }

    @PatchMapping("/{er-id}")
    public ResponseEntity<ExerciseRecord> exerciseRecordModify(@PathVariable Integer memberId, @PathVariable("er-id") Integer erId, @RequestBody ExerciseRecordDto exerciseRecordDto) {
        ExerciseRecord exerciseRecord = exerciseRecordService.modifyExerciseRecord(memberId, erId, exerciseRecordDto.getMemo());

        return new ResponseEntity<>(exerciseRecord, HttpStatus.OK);
    }

    @DeleteMapping("/{er-id}")
    public ResponseEntity<ExerciseRecord> exerciseRecordRemove(@PathVariable Integer memberId, @PathVariable("er-id") Integer erId) {
        exerciseRecordService.removeExerciseRecord(memberId, erId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
