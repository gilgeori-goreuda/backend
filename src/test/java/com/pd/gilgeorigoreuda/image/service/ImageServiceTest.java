package com.pd.gilgeorigoreuda.image.service;

import com.pd.gilgeorigoreuda.image.exception.EmptyImageListException;
import com.pd.gilgeorigoreuda.image.exception.ExceedImageListSizeException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@MockitoSettings
class ImageServiceTest {

    @InjectMocks
    private ImageService imageService;

    @Test
    @DisplayName("입력된 이미지의 수가 0개이면 예외 발생")
    void save_EmptyException() {
        // given
        List<MultipartFile> files = List.of();

        // when & then
        assertThatThrownBy(() -> imageService.save(files))
                .isInstanceOf(EmptyImageListException.class)
                .extracting("errorCode")
                .isEqualTo("I007");
    }

    @Test
    @DisplayName("입력된 이미지의 수가 5개를 초과하면 예외 발생")
    void save_ExceedSizeException() throws IOException {
        // given
        final MockMultipartFile file = new MockMultipartFile(
                "images",
                "static/images/any.png",
                "image/png",
                new FileInputStream("./src/test/resources/static/images/any.png")
        );

        final List<MultipartFile> files = new ArrayList<>(Collections.nCopies(6, file));

        // when & then
        assertThatThrownBy(() -> imageService.save(files))
                .isInstanceOf(ExceedImageListSizeException.class)
                .extracting("errorCode")
                .isEqualTo("I006");
    }

}