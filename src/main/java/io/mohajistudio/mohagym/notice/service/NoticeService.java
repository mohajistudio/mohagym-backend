package io.mohajistudio.mohagym.notice.service;

import io.mohajistudio.mohagym.entity.Notice;
import io.mohajistudio.mohagym.notice.execption.ResourceNotFoundException;
import io.mohajistudio.mohagym.notice.repository.NoticeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class NoticeService {
    @Autowired
    private NoticeRepository noticeRepository;

    // create board rest api

    public Notice createNotice(@RequestBody Notice notice) {

        return noticeRepository.save(notice);
    }


    // list all boards
    public Page<Notice> listAllNotices(Pageable pagable) {
        return noticeRepository.findAll(pagable);
    }


    // get board by id
    public ResponseEntity<Notice> getBoardById(@PathVariable Integer id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board not exist with id :" + id));

        int cnt = notice.getViewCnt();
        notice.setViewCnt(cnt + 1);

        return ResponseEntity.ok(notice);
    }

    // update board
    public ResponseEntity<Notice> updateBoard(@PathVariable Integer id, @RequestBody Notice boardDetails) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board not exist with id :" + id));

        notice.setTitle(boardDetails.getTitle());
        notice.setContent(boardDetails.getContent());

        Notice updatedBoard = noticeRepository.save(notice);
        return ResponseEntity.ok(updatedBoard);
    }

    // delete board
    public ResponseEntity<Map<String, Boolean>> deleteBoard(@PathVariable Integer id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board not exist with id :" + id));

        noticeRepository.delete(notice);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


}