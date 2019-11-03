package com.stackroute.keepnote.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.NoteUser;
import com.stackroute.keepnote.repository.NoteRepository;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class NoteServiceImpl implements NoteService{

	/*
	 * Autowiring should be implemented for the NoteRepository and MongoOperation.
	 * (Use Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	
	
	NoteRepository noteRepository;
	
	@Autowired
	NoteUserSequenceService  noteUserSequenceService;
	
	public NoteServiceImpl(NoteRepository noteRepository)
	{
		this.noteRepository  = noteRepository;
	}
	
	/*
	 * This method should be used to save a new note.
	 */
	public boolean createNote(Note note) {
		
		boolean insertSuccess = false;
		List<Note> noteList = new ArrayList<>();
		noteList.add(note);
		NoteUser  noteUser = noteRepository.findByUserId(note.getNoteCreatedBy());
		if(noteUser!=null)
		{
			noteUser.getNotes().addAll(noteList);
			noteRepository.save(noteUser);
			insertSuccess = true;
		}
		else
		{
			NoteUser noteuser = new NoteUser();
			noteuser.setId(noteUserSequenceService.getNextSequence("noteUserSequence"));
			noteuser.setUserId(note.getNoteCreatedBy());
			noteuser.setNotes(noteList);
			noteRepository.insert(noteuser);
			insertSuccess =  true;
		}
			return insertSuccess;
	}
	
	/* This method should be used to delete an existing note. */

	
	public boolean deleteNote(String userId, int noteId) {
		
		NoteUser noteUser = noteRepository.findByUserId(userId);
		List<Note> finalNoteList = new ArrayList<Note>();
		if(noteUser.getNotes()!=null)
		{
			for(Note note:noteUser.getNotes())
			{
				if(!(note.getNoteId()==noteId))
				{
					finalNoteList.add(note);
				}
			}
			noteUser.setNotes(finalNoteList);
			noteRepository.save(noteUser);
			return true;
		}
		return false;
	}
	
	/* This method should be used to delete all notes with specific userId. */

	
	public boolean deleteAllNotes(String userId) {
		
		NoteUser noteUser = noteRepository.findByUserId(userId);
		noteRepository.delete(noteUser);
		/*if(noteUser.getNotes()!=null)
		{
			List<Note> filteredNotes = new ArrayList<>();
			List<Note>   notes = noteUser.getNotes();
			notes.forEach(note-> {
				if(!note.getNoteCreatedBy().equals(userId))
				{
					filteredNotes.add(note);
				}
			});
			noteUser.setNotes(filteredNotes);
			noteRepository.save(noteUser);
			return true;
		}*/
		return true;
	}

	/*
	 * This method should be used to update a existing note.
	 */
	public Note updateNote(Note note, int id, String userId) throws NoteNotFoundExeption {
		
		try
		{
		NoteUser noteUser = noteRepository.findByUserId(userId);
		if (noteUser.getNotes() != null) {
			List<Note> notes = noteUser.getNotes();
			List<Note> updateNotesList = new ArrayList<>();
			for (Note noteIter : notes) {
				if (noteIter.getNoteId() == id) {
					updateNotesList.add(note);
				} else {
					updateNotesList.add(noteIter);
				}
			}
			noteUser.setNotes(updateNotesList);
			noteRepository.save(noteUser);
		}
		}
		catch(NoSuchElementException exception)
		{
			throw new NoteNotFoundExeption("NoteNotFoundExeption");
		}
		return note;
	}

	/*
	 * This method should be used to get a note by noteId created by specific user
	 */
	public Note getNoteByNoteId(String userId, int noteId) throws NoteNotFoundExeption {
		Note noteReturn = null;
		try
		{
		NoteUser noteUser = noteRepository.findByUserId(userId);
		if (noteUser.getNotes() != null) {
			List<Note> notes = noteUser.getNotes();

			for (Note note : notes) {
				if (note.getNoteId() == noteId) {
					noteReturn = note;
				}
			}
		}
		}
		catch(NoSuchElementException exception)
		{
			throw new NoteNotFoundExeption("NoteNotFoundExeption");
		}
		return noteReturn;
	}

	/*
	 * This method should be used to get all notes with specific userId.
	 */
	public List<Note> getAllNoteByUserId(String userId) {
		
		List<Note> noteList=new ArrayList<Note>();
		NoteUser noteUser = noteRepository.findByUserId(userId);
		if(noteUser!=null) {
			noteList = noteUser.getNotes();
		}
		return noteList;
	}

}