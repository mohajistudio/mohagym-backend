package io.mohajistudio.mohagym.attendance_check.repository;

import io.mohajistudio.mohagym.attendance_check.model.Member;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0l;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {

        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }

    @PostConstruct
    public void init() {
        save(new Member("이찬호"));
        save(new Member("최영민"));
        // 추가 테스트 데이터 등록
    }
}