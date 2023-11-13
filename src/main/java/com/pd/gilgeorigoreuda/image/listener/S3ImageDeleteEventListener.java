package com.pd.gilgeorigoreuda.image.listener;

import com.amazonaws.services.s3.AmazonS3;
import com.pd.gilgeorigoreuda.image.domain.S3ImageDeleteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class S3ImageDeleteEventListener {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.folder}")
    private String folder;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(fallbackExecution = true)
    public void deleteImageFileInS3(final S3ImageDeleteEvent event) {
        String imageName = event.getImageName();

        s3Client.deleteObject(bucket, folder + imageName);
    }

}
