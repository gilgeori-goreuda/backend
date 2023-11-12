package com.pd.gilgeorigoreuda.image;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.pd.gilgeorigoreuda.image.exception.InvalidImageFileException;
import com.pd.gilgeorigoreuda.image.exception.InvalidImagePathException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ImageUploader {

    private static final String CACHE_CONTROL_VALUE = "max-age=3153600";

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.folder}")
    private String folder;

    public List<String> uploadImages(final List<ImageFile> imageFiles) {
        return imageFiles.stream()
                .map(this::uploadImage)
                .toList();
    }

    private String uploadImage(final ImageFile imageFile) {
        final String path = folder + imageFile.getHashedName();

        final ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(imageFile.getContentType());
        metadata.setContentLength(imageFile.getSize());
        metadata.setCacheControl(CACHE_CONTROL_VALUE);

        try (final InputStream inputStream = imageFile.getInputStream()) {
            s3Client.putObject(bucket, path, inputStream, metadata);
        } catch (AmazonServiceException e) {
            log.error("AmazonServiceException: {}", e.getMessage());
            throw new InvalidImagePathException();
        } catch (IOException e) {
            log.error("AmazonServiceException: {}", e.getMessage());
            throw new InvalidImageFileException();
        }
        return imageFile.getHashedName();
    }

}
