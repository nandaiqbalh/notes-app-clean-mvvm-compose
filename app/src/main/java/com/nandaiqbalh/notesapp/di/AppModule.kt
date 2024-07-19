package com.nandaiqbalh.notesapp.di

import android.app.Application
import androidx.room.Room
import com.nandaiqbalh.notesapp.feature_note.data.data_source.NoteDatabase
import com.nandaiqbalh.notesapp.feature_note.data.repository.NoteRepositoryImpl
import com.nandaiqbalh.notesapp.feature_note.domain.repository.NoteRepository
import com.nandaiqbalh.notesapp.feature_note.domain.use_case.AddNoteUseCase
import com.nandaiqbalh.notesapp.feature_note.domain.use_case.DeleteNoteUseCase
import com.nandaiqbalh.notesapp.feature_note.domain.use_case.GetNoteUseCase
import com.nandaiqbalh.notesapp.feature_note.domain.use_case.GetNotesUseCase
import com.nandaiqbalh.notesapp.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Provides
	@Singleton
	fun provideNoteDatabase(app: Application): NoteDatabase {
		return Room.databaseBuilder(
			app,
			NoteDatabase::class.java,
			NoteDatabase.DATABASE_NAME
		).build()
	}

	@Provides
	@Singleton
	fun provideNoteRepository(db: NoteDatabase): NoteRepository {
		return NoteRepositoryImpl(db.noteDao)
	}

	@Provides
	@Singleton
	fun provideNoteUseCases(
		noteRepository: NoteRepository,
	): NoteUseCases {
		return NoteUseCases(
			getNotes = GetNotesUseCase(noteRepository),
			deleteNote = DeleteNoteUseCase(noteRepository),
			addNote = AddNoteUseCase(noteRepository),
			getNote = GetNoteUseCase(noteRepository),
		)
	}
}