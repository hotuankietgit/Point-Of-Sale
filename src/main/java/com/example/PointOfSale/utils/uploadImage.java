package com.example.PointOfSale.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class uploadImage {
   public static void saveFile(String uploadDir, String filename, MultipartFile multipartFile) throws IOException {
       String resourcePath = "src/main/resources/static/" + uploadDir; // Path to the 'static' folder in resources
       Path uploadPath = Paths.get(resourcePath);
       if (!Files.exists(uploadPath)) {
           Files.createDirectories(uploadPath);
       }
       try (InputStream inputStream = multipartFile.getInputStream()) {
           Path filePath = uploadPath.resolve(filename);
           Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
       } catch (IOException e) {
           throw new IOException("Could not save file " + filename, e);
       }

       //       Resource resource = new ClassPathResource("static/" + uploadDir);
//
//       try (InputStream inputStream = multipartFile.getInputStream()) {
//           Path filePath = Path.of(resource.getURI()).resolve(filename);
//           Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//       } catch (IOException e) {
//           throw new IOException("Could not save file " + filename, e);
//       }
//       Path uploadPath = Paths.get(uploadDir);
//       Path uploadPath = Path.of(resource.getURI());
//
//       System.out.println("path: "+ uploadPath);
//
//       if(!Files.exists(uploadPath)){
//           Files.createDirectories(uploadPath);
//       }
//       try(InputStream inputStream = multipartFile.getInputStream()){
//            Path filePath = uploadPath.resolve(filename);
//            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//       }catch (IOException e){
//            throw new IOException("Could not save file " + filename,e);
//       }

   }
}
