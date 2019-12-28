package com.aws.example.demo.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

@Service
public class AmazonClient {

	private AmazonS3 s3client;
	
	@Value("${app.awsServices.bucketName}")
	private String bucketName;
	
	@Value("${cloud.aws.credentials.accessKey}")
	private String accessKey;
	
	@Value("${cloud.aws.credentials.secretKey}")
	private String secretKey;
	
	@Value("${cloud.aws.region.static}")
	private String region;
	
	@SuppressWarnings("deprecation")
	@PostConstruct
    private void initializeAmazon() {
       AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
       this.s3client = new AmazonS3Client(credentials);
	}

	public AmazonS3 getS3client() {
		return s3client;
	}

	public String getBucketName() {
		return bucketName;
	}

	public String getRegion() {
		return region;
	}
	
}
