package com.nandaiqbalh.notesapp.feature_note.domain.use_case

import com.nandaiqbalh.notesapp.feature_note.domain.model.Note
import com.nandaiqbalh.notesapp.feature_note.domain.repository.NoteRepository

class AddNoteUseCase(
	private val repository: NoteRepository,
) {

	suspend operator fun invoke(note: Note) {
		repository.insertNote(note)
	}

}