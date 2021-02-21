package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;
    public String ifError = null;
    public String ifSuccess = null;
    public String successMessage = null;
    public String errorMessage = null;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public String createNote(@ModelAttribute Note note, Authentication authentication, RedirectAttributes redirectAttributes){
        this.ifError = null;
        this.ifSuccess = null;
        this.errorMessage = null;
        this.successMessage = null;
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        note.setUserId(userId);
        int rowsAdded = noteService.createNote(note);
        if (rowsAdded < 0){
            this.errorMessage = "There was an error for adding a note. Please try again";
        }
        if (this.ifError == null) {
            redirectAttributes.addFlashAttribute("ifSuccess",true);
            redirectAttributes.addFlashAttribute("successMessage", "You successfully added a new note");
        } else {
            redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("errorMessage",this.errorMessage);
        }

        return "redirect:/home";
    }

    @PutMapping
    public String updateNote(@ModelAttribute Note note, Authentication authentication, RedirectAttributes redirectAttributes){
        this.ifError = null;
        this.ifSuccess = null;
        this.errorMessage = null;
        this.successMessage = null;
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        note.setUserId(userId);
        int rowsUpdated = noteService.updateNote(note);
        if (rowsUpdated < 0){
            this.errorMessage = "There was an error for updating a note. Please try again";
        }
        if (this.ifError == null) {
            redirectAttributes.addFlashAttribute("ifSuccess",true);
            redirectAttributes.addFlashAttribute("successMessage", "You successfully updated a note");
        } else {
            redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("errorMessage",this.errorMessage);
        }

        return "redirect:/home";
    }

    @DeleteMapping
    public String deleteNote(@ModelAttribute Note note, Authentication authentication, RedirectAttributes redirectAttributes){
        this.ifError = null;
        this.ifSuccess = null;
        this.errorMessage = null;
        this.successMessage = null;
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        note.setUserId(userId);
        int rowsUpdated = noteService.deleteNote(note.getNoteId());
        if (rowsUpdated < 0){
            this.errorMessage = "There was an error for deleting a note. Please try again";
        }
        if (this.ifError == null) {
            redirectAttributes.addFlashAttribute("ifSuccess",true);
            redirectAttributes.addFlashAttribute("successMessage", "You successfully deleted a note");
        } else {
            redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("errorMessage",this.errorMessage);
        }

        return "redirect:/home";

    }
}
