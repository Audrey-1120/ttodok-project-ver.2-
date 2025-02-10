package com.suyuri.ttodokproject.repository;
import org.springframework.stereotype.Repository;

import com.suyuri.ttodokproject.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByMemberId(String memberId);
    Optional<MemberEntity> findByMemberPw(String memberPw);
}
