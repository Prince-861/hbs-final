package com.hms.hotel_booking_system.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class BucketService {

    @Autowired
    private AmazonS3 amazonS3;

    public String uploadFile(MultipartFile file, String bucketName) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        try {
//            It is creating path for the file. The below line what it does--> Before uploading the file in AWS it is moving that file to the temp location in our system.
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            file.transferTo(convFile);//Here it is copying that file to the created path.
            try {
                amazonS3.putObject(bucketName, convFile.getName(), convFile);//Here convFile.getName() is the file name and convFile is the file path.These three parameters that we are supplying now will upload our file to S3.
                return amazonS3.getUrl(bucketName, file.getOriginalFilename()).toString();//Once the file gets uploaded then we can use this utility method(.getUrl()) present in S3 which will give us the uploaded url back as the response and this image is going back to the controller.
            } catch (AmazonS3Exception s3Exception) {
                return "Unable to upload file :" + s3Exception.getMessage();
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to upload file", e);
        }

    }
}