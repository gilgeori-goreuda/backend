package com.pd.gilgeorigoreuda.batch.member.service;

import com.pd.gilgeorigoreuda.member.repository.MemberActiveInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberActiveInfoService {

    private final MemberActiveInfoRepository memberActiveInfoRepository;

    public void updateMemberTotalDistance() {
        memberActiveInfoRepository.updateMemberTotalDistance();
    }

    public void updateMemberTotalVisitCount() {
        memberActiveInfoRepository.updateMemberTotalVisitCount();
    }

    public void updateMemberExp() {
        final double VALUE = 0.01;
        memberActiveInfoRepository.updateMemberExp(VALUE);
    }

}
