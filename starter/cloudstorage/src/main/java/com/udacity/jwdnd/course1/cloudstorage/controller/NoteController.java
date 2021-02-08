package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public String createNote(@ModelAttribute Note note, @ModelAttribute User user, Model model){
        String noteAddError = null;
        String noteAddSuccess = null;
        System.out.println("----------------------------");
        System.out.println(note);
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

        return "";
    }
}
