package com.minio;

import io.minio.MinioClient;
import io.minio.errors.MinioException;
import io.minio.messages.Bucket;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class FileUploader {
    public static void main(String[] args) {
        try {
            //创建Bucket
            MinioClient minioClient=new MinioClient("http://localhost:9001", "minio", "12345678");
            /*boolean isExist=minioClient.bucketExists("my-bucket");
            if(isExist){
                System.out.println("Bucket is exists.");
            }else {
                minioClient.makeBucket("my-bucket");
            }*/

            /*minioClient.putObject("myasiatrip","task3.zip","/home/lwy/文档/task3.zip",null);
            System.out.println("successfully uploaded");*/
            //查找Bucket
            List<Bucket> buckets=minioClient.listBuckets();
            for (Bucket bucket:buckets){
                System.out.println(bucket.creationDate()+","+bucket.name());
            }
            //删除Bucket
            boolean found=minioClient.bucketExists("my-aa");
            if(found){
                minioClient.removeBucket("my-aa");
                System.out.println("mybucket is removed successfully");
            }else {
                System.out.println("not exist");
            }


        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
