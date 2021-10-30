package com.sysouq.sysouqapi.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImageUploadUtil {

    public static void uploadUserAvatar(Long userId, MultipartFile avatar) {
        Path uploadDirectory = Paths.get("C:\\xampp\\tomcat\\webapps\\ROOT\\uploads\\images\\user\\" + userId + "\\");
        try {
            if (!Files.exists(uploadDirectory)) {
                Files.createDirectories(uploadDirectory);
            }

            InputStream inputStream = avatar.getInputStream();
            Path avatarPath = uploadDirectory.resolve("avatar.png");
            Files.copy(inputStream, avatarPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


    public static String uploadItemPhotos(Long itemId, MultipartFile photo) {
        Path uploadDirectory = Paths.get("C:\\xampp\\tomcat\\webapps\\ROOT\\uploads\\images\\items\\" + itemId + "\\");
        try {
            if (!Files.exists(uploadDirectory)) {
                Files.createDirectories(uploadDirectory);
            }
            InputStream inputStream = photo.getInputStream();
            String fileName = System.currentTimeMillis() + ".png";
            Path photoPath = uploadDirectory.resolve(fileName);
            Files.copy(inputStream, photoPath, StandardCopyOption.REPLACE_EXISTING);
            //return "C:\\xampp\\tomcat\\webapps\\ROOT\\uploads\\images\\items\\" + itemId + "\\" + fileName;

            return "https://azizothman.de:8443/uploads/images/items/" + itemId + "/" + fileName;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

    }
}
