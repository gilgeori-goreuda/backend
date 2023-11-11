package com.pd.gilgeorigoreuda.image.service;

import com.pd.gilgeorigoreuda.image.ImageFile;
import com.pd.gilgeorigoreuda.image.ImageUploader;
import com.pd.gilgeorigoreuda.image.dto.response.ImagesResponse;
import com.pd.gilgeorigoreuda.image.exception.EmptyImageListException;
import com.pd.gilgeorigoreuda.image.exception.ExceedImageListSizeException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private static final int MAX_IMAGE_LIST_SIZE = 5;
    private static final int EMPTY_LIST_SIZE = 0;

    private final ImageUploader imageUploader;
//    private final ApplicationEventPublisher publisher;

    public ImagesResponse save(final List<MultipartFile> images) {
        validateSizeOfImages(images);

        final List<ImageFile> imageFiles = images.stream()
                .map(ImageFile::new)
                .toList();
        final List<String> imageNames = uploadImages(imageFiles);

        return new ImagesResponse(imageNames);
    }

    private List<String> uploadImages(final List<ImageFile> imageFiles) {
        return imageUploader.uploadImages(imageFiles);
//        try {
//            return imageUploader.uploadImages(imageFiles);
//        } catch (final ImageException e) {
//            imageFiles.forEach(imageFile -> publisher.publishEvent(new S3ImageEvent(imageFile.getHashedName())));
//            throw e;
//        }
    }

    private void validateSizeOfImages(final List<MultipartFile> images) {
        if (images.size() > MAX_IMAGE_LIST_SIZE) {
            throw new ExceedImageListSizeException();
        }
        if (images.size() == EMPTY_LIST_SIZE) {
            throw new EmptyImageListException();
        }
    }
}
