package com.aws.example.demo.rest;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.aws.example.demo.service.AmazonClient;
import com.aws.example.demo.utils.MyUtils;

@RestController
public class BucketController {

	@Autowired
	private AmazonClient amazonClient;
	
	@Autowired
	private MyUtils myUtils;

	@SuppressWarnings("deprecation")
	@GetMapping("/createBucket")
	public Bucket createBucket() {
		return amazonClient.getS3client().createBucket(amazonClient.getBucketName(), amazonClient.getRegion());
	}
	
	@PostMapping("/uploadFile")
	public PutObjectResult uploadFile(@RequestPart(value="file") MultipartFile file) throws IOException {
		String key = myUtils.generateFileName(file);
		File newfile = myUtils.convertMultiPartToFile(file);
		return amazonClient.getS3client().putObject(amazonClient.getBucketName(), key, newfile);
	}
	
	@DeleteMapping("/deleteFile")
	public String deleteFile(@RequestParam String key) {
		amazonClient.getS3client().deleteObject(amazonClient.getBucketName(), key);
		return "S3Object has been deleted";
	}
	
	@GetMapping("/downloadFile")
	public ObjectMetadata getFile(@Value("${dir}") String destdir, @RequestParam String key) {
		File destFile = new File(destdir + key);
		return amazonClient.getS3client().getObject(new GetObjectRequest(amazonClient.getBucketName(), key),destFile);
	}
	
	@DeleteMapping("/deleteBucket")
	public String deleteBucket() {
		amazonClient.getS3client().deleteBucket(amazonClient.getBucketName());
		return "S3Bucket has been deleted";
	}
	
}
