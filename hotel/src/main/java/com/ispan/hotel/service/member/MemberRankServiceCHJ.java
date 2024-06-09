package com.ispan.hotel.service.member;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.hotel.model.MemberRank;
import com.ispan.hotel.repository.member.MemberRankRepositoryCHJ;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MemberRankServiceCHJ {

    @Autowired
    private MemberRankRepositoryCHJ memberRankRepository;

    public boolean existsByName(String name) {
        if (name != null && name.length() != 0) {
            return memberRankRepository.findByName(name).isPresent();
        }
        return false;
    }

    public boolean existById(Integer mrId) {
        if (mrId != null) {
            return memberRankRepository.existsById(mrId);
        }
        return false;
    }

    public List<MemberRank> find() {
        return memberRankRepository.findAll();
    }

    public MemberRank findById(Integer mrId) {
        if (mrId != null) {
            Optional<MemberRank> optional = memberRankRepository.findById(mrId);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }

    public MemberRank savePhoto(MemberRank gp) {
        return memberRankRepository.save(gp);
    }

    public boolean delete(Integer mrId) {
        if (mrId != null) {
            Optional<MemberRank> optional = memberRankRepository.findById(mrId);
            if (optional.isPresent()) {
                memberRankRepository.deleteById(mrId);
                return true;
            }
        }
        return false;
    }

    public boolean delete(MemberRank bean) {
        if (bean != null && bean.getMrId() != null) {
            Optional<MemberRank> optional = memberRankRepository.findById(bean.getMrId());
            if (optional.isPresent()) {
                memberRankRepository.delete(bean);
                return true;
            }
        }
        return false;
    }
}
