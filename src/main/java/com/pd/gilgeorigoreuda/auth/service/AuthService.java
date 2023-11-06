package com.pd.gilgeorigoreuda.auth.service;

import com.pd.gilgeorigoreuda.auth.dto.request.SignUpRequest;
import com.pd.gilgeorigoreuda.auth.exception.EmailDuplicatedException;
import com.pd.gilgeorigoreuda.fileUpload.service.FileUploadService;
import com.pd.gilgeorigoreuda.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthService {

    @Value("g-memberprofile")
    private String bucket;
    private final MemberRepository memberRepository;
    private final FileUploadService fileUploadService;

    @Transactional
    public String signUp(SignUpRequest request, List<MultipartFile> profileImage) {

        checkDuplicationEmail(request.getEmail());

        fileUploadService.fileUpload(bucket, profileImage)
                .forEach(profileImageUrl -> {
                    memberRepository.save(request.toEntity(profileImageUrl));
                });

        return "회원가입 성공";

    }

    private void checkDuplicationEmail(String email) {

        if (memberRepository.findByEmail(email).isPresent()) {
            throw new EmailDuplicatedException();
        }

    }
}
