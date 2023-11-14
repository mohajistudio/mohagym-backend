package io.mohajistudio.mohagym.repository;

import io.mohajistudio.mohagym.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByEmailAndDeletedAtIsNull(String userId);
    Member findByEmailAndPasswordAndDeletedAtIsNull(String userId, String password);
    Member findByRefreshTokenAndDeletedAtIsNull(String refreshToken);
    Page<Member> findByMemberProfileNameContainingAndDeletedAtIsNull(String name, Pageable pageable);
    Member findByIdAndDeletedAtIsNull(Long Id);
    Page<Member> findAllByDeletedAtIsNull(Pageable pageable);

}
