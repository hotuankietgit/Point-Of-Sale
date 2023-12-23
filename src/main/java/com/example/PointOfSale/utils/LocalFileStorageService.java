package com.example.PointOfSale.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class LocalFileStorageService implements FileStorageService{
    @Override
    public boolean storeImageToPublicFolder(MultipartFile file, String fileName) {
        try{
            String resourcePath = "src/main/resources/static/image";
            Path uploadPath = Paths.get(resourcePath);

            String originalName = file.getOriginalFilename();
            String fileFinal = fileName + "." + originalName.substring(originalName.lastIndexOf(".") + 1);
            Path path = uploadPath.resolve(fileFinal);
            System.out.println(path);

            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
