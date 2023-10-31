package io.mohajistudio.mohagym.repository;

import io.mohajistudio.mohagym.entity.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {
    MemberProfile findByNameAndDeletedAtIsNull(String name);

    MemberProfile findByIdAndDeletedAtIsNull(Long Id);
}
