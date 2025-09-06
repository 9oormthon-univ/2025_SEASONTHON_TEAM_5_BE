package com.goormthon.whattoeat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goormthon.whattoeat.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {

}
