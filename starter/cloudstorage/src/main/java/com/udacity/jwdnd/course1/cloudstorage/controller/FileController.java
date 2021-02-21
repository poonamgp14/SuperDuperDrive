package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;
    private final UserService userService;
    public String ifError = null;
    public String ifSuccess = null;
    public String successMessage = null;
    public String errorMessage = null;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping
    public String uploadFile(@RequestParam("fileUpload") MultipartFile multipartFile, Authentication authentication,
                                 RedirectAttributes redirectAttributes) {
        this.ifError = null;
        this.ifSuccess = null;
        this.errorMessage = null;
        this.successMessage = null;
        if (multipartFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("ifSuccess", false);
            redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("message", "File not selected to upload");
            return "redirect:/home";
        }

        User user = this.userService.getUser(authentication.getName());
        Integer userId = user.getUserId();

        if (fileService.isFilenameAvailable(multipartFile.getOriginalFilename(), userId)) {

            redirectAttributes.addFlashAttribute("ifSuccess", false);
            redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("ErrorMessage", "file name already exists");
            return "redirect:/home";
        }
        try {
            fileService.createFile(multipartFile, userId);
            redirectAttributes.addFlashAttribute("ifSuccess", true);
            redirectAttributes.addFlashAttribute("successMessage", "New File added successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("errorMessage", "System error!" + e.getMessage());
        }
        return "redirect:/home";
    }

    @DeleteMapping
    public String deleteFile(@ModelAttribute File file, Authentication authentication,RedirectAttributes redirectAttributes ) {
        System.out.println("i m in delete cont");
        System.out.println(file.getFileId());
//        User user = this.userService.getUser(authentication.getName());
//        Integer userId = user.getUserId();

        try {
            fileService.deleteFile(file.getFileId());
            redirectAttributes.addFlashAttribute("ifSuccess", true);
            redirectAttributes.addFlashAttribute("successMessage", "file Deleted");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("errorMessage", "System error!" + e.getMessage());
        }
        return "redirect:/home";
    }
//
//    @GetMapping("/download/{fileId}")
//    public ResponseEntity<Resource> download(@PathVariable("fileId") Integer fileId) {
//        File file = fileService.getFileById(fileId);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(httpHeaders.CONTENT_DISPOSITION, "attachment; filename = " + file.getFilename());
//        httpHeaders.add("Cache-control", "no-cache, no-store, must-revalidate");
//        httpHeaders.add("Pragma", "no-cache");
//        httpHeaders.add("Expires", "0");
//        ByteArrayResource resource = new ByteArrayResource(file.getFiledata());
//        return ResponseEntity.ok()
//                .headers(httpHeaders)
//                .body(resource);
//
//    }
}

