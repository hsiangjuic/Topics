package com.ispan.hotel.service.booking;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.hotel.model.MemberRank;
import com.ispan.hotel.repository.booking.MemberRankRepositoryYTH;

@Service
public class MemberRankServiceYTH {

	@Autowired
	private MemberRankRepositoryYTH memberRankRepository;
	
	public MemberRank findById(Integer id) {
		if(id != null) {
			Optional<MemberRank> opt = memberRankRepository.findById(id);
			if(opt.isPresent()) {
				return opt.get();
			}
		}
		return null;
	}
}
