package com.swayam.demo.springboot.googleauth.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemUtil {

    private final String imageWriteDirectory;

    public FileSystemUtil(@Value("${app.config.server.image-write-directory}") String imageWriteDirectory) {
	this.imageWriteDirectory = imageWriteDirectory;

    }

    public Path getImageSaveLocation(String imageFileName) {
	return Paths.get(imageWriteDirectory, imageFileName);
    }

    public Path saveMultipartFileAsImage(MultipartFile image) {
	Path imageOutputFilePath = getImageSaveLocation(image.getOriginalFilename());
	try {
	    Files.copy(image.getInputStream(), imageOutputFilePath);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
	return imageOutputFilePath;
    }

}
