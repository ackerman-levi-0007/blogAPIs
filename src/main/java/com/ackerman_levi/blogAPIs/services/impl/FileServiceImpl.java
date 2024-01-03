package com.ackerman_levi.blogAPIs.services.impl;

import com.ackerman_levi.blogAPIs.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        //File Name
        String fileName = file.getOriginalFilename();

        //Full Path
        String randomUUID = UUID.randomUUID().toString();
        String updatedFileName = randomUUID.concat(fileName.substring(fileName.lastIndexOf(".")));
        String filePath = path + File.separator + updatedFileName;

        //Create Folder if not created
        File f = new File(path);
        if(!f.exists()){
            f.mkdirs();
        }

        //File Copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return updatedFileName;
    }

    @Override
    public InputStream downloadFile(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        return new FileInputStream(fullPath);
    }
}
