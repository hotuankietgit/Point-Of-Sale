package com.example.PointOfSale.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    boolean storeImageToPublicFolder(MultipartFile file, String fileName);
}
