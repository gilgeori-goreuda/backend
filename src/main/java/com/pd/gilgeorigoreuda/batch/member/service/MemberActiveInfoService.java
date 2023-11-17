package com.pd.gilgeorigoreuda.batch.member.service;

import com.pd.gilgeorigoreuda.member.repository.MemberActiveInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberActiveInfoService {

    private final MemberActiveInfoRepository memberActiveInfoRepository;

    @Transactional(readOnly = false)
    public void updateMemberTotalDistance() {
        memberActiveInfoRepository.updateMemberTotalDistance();
    }

    @Transactional(readOnly = false)
    public void updateMemberTotalVisitCount() {
        memberActiveInfoRepository.updateMemberTotalVisitCount();
    }

    @Transactional(readOnly = false)
    public void updateMemberExp() {
        final double VALUE = 0.01;
        memberActiveInfoRepository.updateMemberExp(VALUE);
    }

}
