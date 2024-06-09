package com.ispan.hotel.repository.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.hotel.model.MemberRank;

public interface MemberRankRepositoryCHJ extends JpaRepository<MemberRank, Integer> {
    Optional<MemberRank> findByName(String name);
}
