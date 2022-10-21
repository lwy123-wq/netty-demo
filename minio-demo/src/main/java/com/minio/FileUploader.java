package com.minio;

import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.MinioException;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import io.minio.messages.Upload;
import org.omg.PortableServer.THREAD_POLICY_ID;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class FileUploader {

    public static void main(String[] args) {
        try {
            //创建Bucket
            MinioClient minioClient=new MinioClient("http://localhost:9001", "admin", "12345678");
            /*boolean isExist=minioClient.bucketExists("my-bucket");
            if(isExist){
                System.out.println("Bucket is exists.");
            }else {
                minioClient.makeBucket("my-bucket");
            }*/

            /*minioClient.putObject("myasiatrip","task3.zip","/home/lwy/文档/task3.zip",null);
            System.out.println("successfully uploaded");*/

            //查找Bucket
            /*List<Bucket> buckets=minioClient.listBuckets();
            for (Bucket bucket:buckets){
                System.out.println(bucket.creationDate()+","+bucket.name());
            }*/

            //删除Bucket
            /*boolean found=minioClient.bucketExists("my-aa");
            if(found){
                minioClient.removeBucket("my-aa");
                System.out.println("mybucket is removed successfully");
            }else {
                System.out.println("not exist");
            }*/

            //列出某个存储桶中的所有对象
            /*boolean found=minioClient.bucketExists("myasiatrip");
            if(found){
                Iterable<Result<Item>> myObjects=minioClient.listObjects("myasiatrip");
                for(Result<Item> result:myObjects){
                    Item item=result.get();
                    System.out.println(item.lastModified()+","+item.size()+","+item.objectName());
                }
            }else {
                System.out.println("myasiatrip dose not exist");
            }*/

            //列出存储桶中被部分上传的对象
            /*boolean found=minioClient.bucketExists("myasiatrip");
            if(found){
                Iterable<Result<Upload>> myObjects=minioClient.listIncompleteUploads("myasiatrip");
                for(Result<Upload> result: myObjects){
                    Upload upload=result.get();
                    System.out.println(upload.uploadId()+","+upload.objectName());
                }
            }else {
                System.out.println("myasiatrip dose not exist");
            }*/

            //获得指定对象前缀的存储桶策略
            //minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket("myasiatrip").config());
            //System.out.println("Current policy:"+minioClient.getBucketPolicy("myasiatrip"));

            //删除一个未完整上传的对象
            /*minioClient.removeIncompleteUpload("my-bucket","myobject"); //对象
            System.out.println("successfully removed all incomplete upload session of my-bucketname/my-objectname");*/

            //生成一个给HTTP GET请求用的presigned URL。浏览器/移动端的客户端可以用这个URL进行下载，即使其所在的存储桶是私有的。
            // 这个presigned URL可以设置一个失效时间，默认值是7天。
            /*String url=minioClient.presignedGetObject("myasiatrip","task3.zip",60*60*24);
            System.out.println(url);*/

            //生成一个给HTTP PUT请求用的presigned URL。浏览器/移动端的客户端可以用这个URL进行上传，即使其所在的存储桶是私有的。
            // 这个presigned URL可以设置一个失效时间，默认值是7天。
            String url1=minioClient.presignedPutObject("myasiatrip","task3.zip",60*60*24);
            System.out.println(url1);

            //允许给POST请求的presigned URL设置策略，比如接收对象上传的存储桶名称的策略，key名称前缀，过期策略。


        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
