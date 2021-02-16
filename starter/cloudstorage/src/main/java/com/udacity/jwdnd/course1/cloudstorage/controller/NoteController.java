package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public String createNote(@ModelAttribute Note note, Authentication authentication, Model model){
        String noteAddError = null;
        String noteAddSuccess = null;
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        note.setUserId(userId);
        int rowsAdded = noteService.createNote(note);
        if (rowsAdded < 0){
            noteAddError = "There was an error for adding a note. Please try again";
        }
        if (noteAddError == null) {
            model.addAttribute("noteAddSuccess", true);
        } else {
            model.addAttribute("noteAddError", noteAddError);
        }

        return "redirect:/home";
    }

    @PutMapping
    public String updateNote(@ModelAttribute Note note, Authentication authentication, Model model){
        System.out.println("i m in update note");
        String noteEditError = null;
        String noteEditSuccess = null;
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        note.setUserId(userId);
        int rowsUpdated = noteService.updateNote(note);
        if (rowsUpdated < 0){
            noteEditError = "There was an error for updating a note. Please try again";
        }
        if (noteEditError == null) {
            model.addAttribute("noteEditSuccess", true);
        } else {
            model.addAttribute("noteEditError", noteEditError);
        }

        return "redirect:/home";
    }
}
