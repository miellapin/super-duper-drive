package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Files;
import com.udacity.jwdnd.course1.cloudstorage.entity.Users;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final FileService fileService;
    private final UserService userService;
    private final FileMapper fileMapper;

    public HomeController(FileService fileService, UserService userService, FileMapper fileMapper) {
        this.fileService = fileService;
        this.userService = userService;
        this.fileMapper = fileMapper;
    }

    @GetMapping
    public String getHome(Model model) {
        List<Files> filesList = fileMapper.findAllFiles();
        if(filesList.isEmpty()) {
            model.addAttribute("noFiles", false);
            return "home";
        }
        model.addAttribute("filesList", filesList);
        return "home";
    }

    @PostMapping("/upload/file")
    public String uploadFile(@ModelAttribute("file")MultipartFile file, Model model, Principal principal) throws IOException {
        String errorMessage = null;
        Users user = userService.getUserByUsername(principal.getName());
        boolean legitFileName = fileService.checkFileNames(file);
        if(legitFileName) {
            int id = fileService.addFile(file, user);
            if(id < 0)
                errorMessage = "An error occurred during file upload";

            if(errorMessage == null) {
                model.addAttribute("uploadFileSuccess", true);
            }
            else {
                model.addAttribute("uploadFileError", errorMessage);
            }
            List<Files> filesList = fileMapper.findAllFiles();
            model.addAttribute("filesList", filesList);
            return "home";
        }
        errorMessage = "Files with identical names cannot be uploaded!";
        List<Files> filesList = fileMapper.findAllFiles();
        model.addAttribute("filesList", filesList);
        model.addAttribute("uploadFileSuccess", false);
        model.addAttribute("uploadFileError", errorMessage);
        return "home";
    }

    @GetMapping(value = "/view/{fileId}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getFile(@PathVariable("fileId") Integer fileId) throws IOException{
        try {
            Files downloadedFile = fileMapper.findFile(fileId);
            return IOUtils.toByteArray(downloadedFile.getFiledata());
        }
        catch (IOException e) {
            throw new RuntimeException("IOError writing file to output stream");
        }
    }

    @GetMapping(value = "/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId, Model model){
        fileMapper.delete(fileId);
        List<Files> filesList = fileMapper.findAllFiles();
        model.addAttribute("filesList", filesList);
        model.addAttribute("deleteSuccess", true);
        return "home";
    }
}
