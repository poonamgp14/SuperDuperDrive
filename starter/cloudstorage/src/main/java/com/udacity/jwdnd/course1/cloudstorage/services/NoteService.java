package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int createNote(Note note) {
        return noteMapper.insert(new Note(null, note.getTitle(), note.getDescription(), note.getUserId()));
    }

    public List<Note> getNotes(Integer userId){
        return noteMapper.getNotes(userId);
    }

    public int updateNote(Note note) {
        return noteMapper.updateNote(new Note(note.getNoteId(), note.getTitle(), note.getDescription(), note.getUserId()));
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
