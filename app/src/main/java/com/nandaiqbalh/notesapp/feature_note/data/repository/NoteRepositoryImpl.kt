package com.nandaiqbalh.notesapp.feature_note.data.repository

import com.nandaiqbalh.notesapp.feature_note.data.data_source.NoteDao
import com.nandaiqbalh.notesapp.feature_note.domain.model.Note
import com.nandaiqbalh.notesapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
	private val noteDao: NoteDao,
) : NoteRepository {

	override fun getNotes(): Flow<List<Note>> {
		return noteDao.getNotes()
	}

	override suspend fun getNoteById(id: Int): Note? {
		return noteDao.getNoteById(id)
	}

	override suspend fun insertNote(note: Note) {
		return noteDao.insertNote(note)
	}

	override suspend fun deleteNote(note: Note) {
		return noteDao.deleteNote(note)
	}
}