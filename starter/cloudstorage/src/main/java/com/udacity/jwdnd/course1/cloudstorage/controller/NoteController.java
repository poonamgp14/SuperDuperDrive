package com.udacity.jwdnd.course1.cloudstorage.controller;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public String createNote(@ModelAttribute Note note, Model model){
        String noteAddError = null;
        String noteAddSuccess = null;
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
