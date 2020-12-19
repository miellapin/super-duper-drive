package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Notes;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public void addNote(Notes note) {
        noteMapper.addNote(note);
    }

    public List<Notes> getAllNotes() {
        return noteMapper.findAllNotes();
    }

    public void deleteNote(Integer noteid) {
        noteMapper.delete(noteid);
    }
}
