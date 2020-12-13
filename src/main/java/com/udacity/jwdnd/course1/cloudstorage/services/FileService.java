package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Files;
import com.udacity.jwdnd.course1.cloudstorage.entity.Users;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public boolean checkFileNames(MultipartFile file) {
        List<Files> uploadedFiles = fileMapper.findAllFiles();
        for(Files selectedFile : uploadedFiles) {
            if(selectedFile.getFilename().equalsIgnoreCase(file.getName()))
                return false;
        }
        return true;
    }

    public Integer addFile(MultipartFile file, Users user) throws IOException {
        Files storedFile = new Files();
        InputStream inputStream =  new BufferedInputStream(file.getInputStream());
        storedFile.setFiledata(inputStream);
        storedFile.setContenttype(file.getContentType());
        storedFile.setFilename(file.getOriginalFilename());
        storedFile.setFilesize(((Long) file.getSize()).toString());
        storedFile.setUserid(user.getUserid());
        return fileMapper.addFiles(storedFile);
    }

}
