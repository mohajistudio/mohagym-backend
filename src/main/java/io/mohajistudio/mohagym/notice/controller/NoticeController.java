package io.mohajistudio.mohagym.notice.controller;


import io.mohajistudio.mohagym.entity.Notice;
import io.mohajistudio.mohagym.notice.dto.ResponseDTO;
import io.mohajistudio.mohagym.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class NoticeController {


    @Autowired
    private NoticeService noticeService;

    // create board rest api
    @PostMapping("/notice")
    public ResponseEntity<?> createNotice(@Validated @RequestBody ResponseDTO responseDTO) {
        Notice notice = new Notice();
        notice.setTitle(notice.getTitle());
        notice.setContent(responseDTO.getContent());
        notice.setViewCnt(responseDTO.getViewCnt());

        return ResponseEntity.ok(noticeService.createNotice(notice));
    }

    // list all boards
    @GetMapping("/notice")
    public Page<Notice> listAllNotices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return noticeService.listAllNotices(PageRequest.of(page, size));
    }

    // get board by id
    @GetMapping("/boards/{id}")
    public ResponseEntity<Notice> getBoardById(@PathVariable Integer id) {
        return noticeService.getBoardById(id);
    }

    // update board
    @PutMapping("/boards/{id}")
    public ResponseEntity<Notice> updateBoard(
            @PathVariable Integer id, @RequestBody Notice boardDetails) {
        return noticeService.updateBoard(id, boardDetails);
    }

    // delete board
    @DeleteMapping("/boards/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteBoard(@PathVariable Integer id) {
        return noticeService.deleteBoard(id);
    }

}