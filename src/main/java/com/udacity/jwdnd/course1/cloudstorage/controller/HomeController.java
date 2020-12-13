package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Files;
import com.udacity.jwdnd.course1.cloudstorage.entity.Users;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

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
            return "home";
        }
        errorMessage = "Files with identical names cannot be uploaded!";
        model.addAttribute("uploadFileSuccess", false);
        model.addAttribute("uploadFileError", errorMessage);
        return "home";
    }
}
