package com.nandaiqbalh.notesapp.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandaiqbalh.notesapp.feature_note.domain.model.Note
import com.nandaiqbalh.notesapp.feature_note.domain.use_case.NoteUseCases
import com.nandaiqbalh.notesapp.feature_note.domain.util.NoteOrder
import com.nandaiqbalh.notesapp.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
	private val noteUseCases: NoteUseCases,
) : ViewModel() {

	private val _state = mutableStateOf(NotesState())
	val state: State<NotesState> = _state

	private var recentlyDeletedNote: Note? = null

	private var getNotesJob: Job? = null

	init {
		getNotes(NoteOrder.Date(OrderType.Descending))
	}

	fun onEvent(notesEvent: NotesEvent) {
		when (notesEvent) {
			is NotesEvent.Order -> {

				if (state.value.noteOrder::class == notesEvent.noteOrder::class &&
					state.value.noteOrder.orderType == notesEvent.noteOrder.orderType
				) {
					return
				}

			}

			is NotesEvent.DeleteNote -> {
				viewModelScope.launch {
					noteUseCases.deleteNote(notesEvent.note)
					recentlyDeletedNote = notesEvent.note
				}

			}

			is NotesEvent.RestoreNote -> {

				viewModelScope.launch {
					noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
					recentlyDeletedNote = null
				}
			}

			is NotesEvent.ToggleOrderSection -> {
				_state.value = state.value.copy(
					isOrderSectionVisible = !state.value.isOrderSectionVisible
				)
			}

		}
	}

	private fun getNotes(notesOrder: NoteOrder) {

		getNotesJob?.cancel()

		noteUseCases.getNotes(notesOrder)
			.onEach { notes ->
				_state.value = state.value.copy(
					notes = notes,
					noteOrder = notesOrder
				)
			}.launchIn(viewModelScope)
	}

}