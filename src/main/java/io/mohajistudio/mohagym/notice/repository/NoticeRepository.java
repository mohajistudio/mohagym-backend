package io.mohajistudio.mohagym.notice.repository;

import io.mohajistudio.mohagym.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {

}
