package com.jeeplus.modules.app.utils;


import com.jeeplus.sys.utils.DictUtils;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;
import com.microsoft.windowsazure.services.core.storage.utils.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

public class AzureUpload {

	public static String uploadToBlob(String imageBase64, String blobName,String fileName,String fileType) {
		 byte[] buffer = Base64.decode(imageBase64);
		 return uploadToBlob(buffer,blobName,fileName,fileType);
	}
	public static String uploadToBlob(String imageBase64, String blobName,String fileName) {
		 byte[] buffer =Base64.decode(imageBase64);
		 return uploadToBlob(buffer,blobName,fileName,"image/jpeg");
	}
	
	public static String uploadToBlob(byte[] buffer, String blobName,String fileName,String fileType) {
		try{
			if(buffer==null||buffer.length==0)return "";
			CloudStorageAccount storageAccount;
			if(StringUtils.isNotBlank(
					DictUtils.getDictValue("StorageConnetion", "StorageConnetion", ""))){
				storageAccount = 
						CloudStorageAccount.parse(
								DictUtils.getDictValue("StorageConnetion", "StorageConnetion", ""));
			}
			else {
				storageAccount = 
						CloudStorageAccount.parse(
								"DefaultEndpointsProtocol=https;AccountName=bowkerstorage;AccountKey=i9cQSmIHnrqbGOGQnT1laRSxMS+TcOHYrJz0r9nevygrJH9c7RruvS/hs+JJm+WvwbjUxt/o2X6dr84OslysZQ==;EndpointSuffix=core.windows.net");
			}

			// Create the blob client.
			CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

			// Get a reference to a container.
			// The container name must be lower case
			CloudBlobContainer container = blobClient.getContainerReference(blobName);

			// Create the container if it does not exist.
			container.createIfNotExists();

			// Create a permissions object.
			BlobContainerPermissions containerPermissions = new BlobContainerPermissions();

			// Include public access in the permissions object.
			containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);

			// Set the permissions on the container.
			container.uploadPermissions(containerPermissions);

			CloudBlockBlob blob = container.getBlockBlobReference(fileName);  
			blob.getProperties().setContentType(fileType);  
			blob.uploadFromByteArray(buffer, 0, buffer.length);
			return fileName;
		}catch (IOException | StorageException | URISyntaxException | InvalidKeyException e){
			return "";
		}
	}
	
	
	public static String uploadToBlob(MultipartFile file, String BlobName,String Prefix) {
		try{
			if(file==null||file.getSize()==0)return "";
			CloudStorageAccount storageAccount;
			//DictUtils.getDictValue("", type, defaultLabel)
			if(StringUtils.isNotBlank(DictUtils.getDictValue("StorageConnetion", "StorageConnetion", "")))
			{
				storageAccount = CloudStorageAccount.parse(DictUtils.getDictValue("StorageConnetion", "StorageConnetion", ""));
			}
			else storageAccount = CloudStorageAccount.parse("DefaultEndpointsProtocol=https;AccountName=bowkerstorage;AccountKey=i9cQSmIHnrqbGOGQnT1laRSxMS+TcOHYrJz0r9nevygrJH9c7RruvS/hs+JJm+WvwbjUxt/o2X6dr84OslysZQ==;EndpointSuffix=core.windows.net");

			// Create the blob client.
			CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

			// Get a reference to a container.
			// The container name must be lower case
			CloudBlobContainer container = blobClient.getContainerReference(BlobName);

			// Create the container if it does not exist.
			container.createIfNotExists();

			// Create a permissions object.
			BlobContainerPermissions containerPermissions = new BlobContainerPermissions();

			// Include public access in the permissions object.
			containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);

			// Set the permissions on the container.
			container.uploadPermissions(containerPermissions);

			// Define the path to a local file.
			String temp;
			if(StringUtils.isNotBlank(Prefix))
				temp = Prefix + "-" +file.getOriginalFilename();
			else 
				temp = file.getOriginalFilename();
			CloudBlockBlob blob = container.getBlockBlobReference(temp);
			blob.getProperties().setContentType(file.getContentType());  
			byte[] 	buffer = file.getBytes();
			
			blob.uploadFromByteArray(buffer, 0, buffer.length);
			//String test = container.getStorageUri().getPrimaryUri().toString()+ "/" +file.getOriginalFilename();
			//String test = Prefix + "-" +file.getOriginalFilename();
			return temp;
		}catch (IOException | StorageException | URISyntaxException | InvalidKeyException e){
			return "";
		}
	}
	public boolean deleteBlob(String fileName,String BlobName) {
		try{
			if(StringUtils.isBlank(fileName))return false;
			CloudStorageAccount storageAccount;
			//DictUtils.getDictValue("", type, defaultLabel)
			if(StringUtils.isNotBlank(DictUtils.getDictValue("StorageConnetion", "StorageConnetion", "")))
			{
				storageAccount = CloudStorageAccount.parse(DictUtils.getDictValue("StorageConnetion", "StorageConnetion", ""));
			}
			else storageAccount = CloudStorageAccount.parse("DefaultEndpointsProtocol=https;AccountName=bowkerstorage;AccountKey=i9cQSmIHnrqbGOGQnT1laRSxMS+TcOHYrJz0r9nevygrJH9c7RruvS/hs+JJm+WvwbjUxt/o2X6dr84OslysZQ==;EndpointSuffix=core.windows.net");

			// Create the blob client.
			CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

			// Get a reference to a container.
			// The container name must be lower case
			CloudBlobContainer container = blobClient.getContainerReference(BlobName);

			// Create the container if it does not exist.
			container.createIfNotExists();

			// Create a permissions object.
			BlobContainerPermissions containerPermissions = new BlobContainerPermissions();

			// Include public access in the permissions object.
			containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);

			// Set the permissions on the container.
			container.uploadPermissions(containerPermissions);

			// Define the path to a local file.
			CloudBlockBlob blob = container.getBlockBlobReference(fileName);
			boolean result = false;
			result = blob.deleteIfExists();
			return result;
		}catch (StorageException | URISyntaxException | InvalidKeyException e){
			return false;
		}
	}
	
	/*
	public static void main(String[] args) {
		MediaFile mediaFile = new MediaFile();
		mediaFile.setExtension("jpg");
		mediaFile.setFilename("image_20170606213500.jpg");
		mediaFile.setFilepath("C:/Users/chenxia.he/Desktop/新建文件夹/123.png");
		mediaFile.setFileType("image");
		mediaFile.setMimeType("image/*");
		mediaFile.setRealname("image_20170606213500.jpg");
		mediaFile.setType("1");
		AzureUpload uploadAzure = new AzureUpload();
		uploadAzure.getCloudPath(mediaFile);
		System.out.println("");
	}*/
}
