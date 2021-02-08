package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;



@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int createNote(Note note) {
        return noteMapper.insert(new Note(null, note.getTitle(), note.getDescription(), note.getUserId()));
    }
//
//    public boolean isUsernameAvailable(String username) {
//        return userMapper.getUser(username) == null;
//    }
//
//    public User getUser(String username) {
//        return userMapper.getUser(username);
//    }
}
