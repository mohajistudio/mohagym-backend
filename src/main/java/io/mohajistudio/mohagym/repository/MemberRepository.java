package io.mohajistudio.mohagym.repository;

import io.mohajistudio.mohagym.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByUserId(String userId);
    Member findByUserIdAndPassword(String userId, String password);
}
