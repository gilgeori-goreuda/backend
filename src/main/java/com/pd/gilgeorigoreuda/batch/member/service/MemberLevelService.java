package com.pd.gilgeorigoreuda.batch.member.service;

import com.pd.gilgeorigoreuda.member.repository.MemberActiveInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberLevelService {

    private final MemberActiveInfoRepository memberActiveInfoRepository;

    public void updateMemberLevel() {
        memberActiveInfoRepository.updateMemberLevel();
    }
}
