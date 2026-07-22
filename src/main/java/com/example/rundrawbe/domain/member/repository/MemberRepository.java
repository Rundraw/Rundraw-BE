package com.example.rundrawbe.domain.member.repository;

import com.example.rundrawbe.domain.member.entity.Member;
import com.example.rundrawbe.domain.member.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findBySocialTypeAndSocialUid(SocialType providerId, String socialUid);
}
