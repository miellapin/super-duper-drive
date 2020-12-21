package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Files;
import com.udacity.jwdnd.course1.cloudstorage.entity.Notes;
import com.udacity.jwdnd.course1.cloudstorage.entity.Users;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    private final NoteService noteService;

    public HomeController(FileService fileService, UserService userService, FileMapper fileMapper, NoteService noteService) {
        this.fileService = fileService;
        this.userService = userService;
        this.fileMapper = fileMapper;
        this.noteService = noteService;
    }

    @GetMapping
    public String getHome(Model model) {
        getAllItems(model);
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
            getAllItems(model);
            return "home";
        }
        errorMessage = "Files with identical names cannot be uploaded!";
        getAllItems(model);
        model.addAttribute("uploadFileSuccess", false);
        model.addAttribute("uploadFileError", errorMessage);
        return "home";
    }

    @GetMapping(value = "/view/{fileId}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getFile(@PathVariable("fileId") Integer fileId, Model model) throws IOException{
        try {
            getAllItems(model);
            Files downloadedFile = fileMapper.findFile(fileId);
            return IOUtils.toByteArray(downloadedFile.getFiledata());
        }
        catch (IOException e) {
            throw new RuntimeException("IOError writing file to output stream");
        }
    }

    @GetMapping(value = "/delete/file/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId, Model model){
        fileMapper.delete(fileId);
        List<Files> filesList = fileMapper.findAllFiles();
        model.addAttribute("filesList", filesList);
        model.addAttribute("deleteSuccess", true);
        getAllItems(model);
        return "home";
    }

    @PostMapping(value = "/create/note")
    public String createNote(@ModelAttribute("note") Notes note, Model model,
                             Principal principal) {
        Users user = userService.getUserByUsername(principal.getName());
        note.setUserid(user.getUserid());
        try {
            if(note.getNoteid() != null) {
                noteService.updateNote(note);
                model.addAttribute("noteUpdateSuccess", true);
                model.addAttribute("noteUpdateSuccessMessage", "The note has been updated!");
                getAllItems(model);
                return "home";
            }
            noteService.addNote(note);
            model.addAttribute("noteSuccess", true);
            model.addAttribute("noteSuccessMessage", "New note added!");
        }
        catch (Exception e) {
            model.addAttribute("noteError", true);
            model.addAttribute("noteErrorMessage", "Cannot find Note" + e.getMessage());
        }
        getAllItems(model);
        return "home";
    }

    @GetMapping(value = "/delete/note/{noteid}")
    public String deleteNote(@PathVariable("noteid") Integer noteid, Model model){
        noteService.deleteNote(noteid);
        model.addAttribute("noteDeleteSuccess", true);
        getAllItems(model);
        return "home";
    }

    public void getAllItems(Model model) {
        List<Files> filesList = fileMapper.findAllFiles();
        List<Notes> noteList = noteService.getAllNotes();
        model.addAttribute("noteList", noteList);
        model.addAttribute("filesList", filesList);
    }
}
