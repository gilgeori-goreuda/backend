//package com.pd.gilgeorigoreuda.fileUpload.service;
//
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@RequiredArgsConstructor
//@Service
//public class FileUploadService {
//    private final UUID uuid = UUID.randomUUID();
//
//    private final AmazonS3Client amazonS3Client;
//
//    public List<String> fileUpload(final String bucket, final List<MultipartFile> files) {
//
//        List<String> filesName = new ArrayList<>();
//
//        files.forEach(file -> {
//            String fileName = uuid + file.getOriginalFilename();
//            ObjectMetadata objectMetadata = new ObjectMetadata();
//            objectMetadata.setContentLength(file.getSize());
//            objectMetadata.setContentType(file.getContentType());
//
//            try(InputStream inputStream = file.getInputStream()) {
//                amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
//            }
//            catch (IOException e) {
//                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
//                        "업로드 에러");
//            }
//
//            filesName.add(amazonS3Client.getUrl(bucket, fileName).toString());
//
//        });
//        return filesName;
//    }
//}
