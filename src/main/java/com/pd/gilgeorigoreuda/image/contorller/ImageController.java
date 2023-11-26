package com.pd.gilgeorigoreuda.image.contorller;

import com.pd.gilgeorigoreuda.image.dto.response.ImagesResponse;
import com.pd.gilgeorigoreuda.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<ImagesResponse> uploadImages(@RequestPart final List<MultipartFile> images) {
        final ImagesResponse imagesResponse = imageService.save(images);

        final String firstImageName = imagesResponse.getImageNames().get(0);

        return ResponseEntity
                .created(URI.create(firstImageName))
                .body(imagesResponse);
    }

}
