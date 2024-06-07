package com.example.notescompose.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notescompose.model.Note

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotesViewModel : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

//    TODO - implement title and description state on rotation
//    private val _title = MutableStateFlow("")
//    val title: StateFlow<String> = _title
//
//    private val _description = MutableStateFlow("")
//    val description: StateFlow<String> = _description
//
//    fun setTitle(newTitle: String) {
//        _title.value = newTitle
//    }
//
//    fun setDescription(newDescription: String) {
//        _description.value = newDescription
//    }

    fun addOrUpdateNote(note: Note) {
        viewModelScope.launch {
            val currentNotes = _notes.value.toMutableList()
            val existingNoteIndex = currentNotes.indexOfFirst { it.id == note.id }
            if (existingNoteIndex >= 0) {
                currentNotes[existingNoteIndex] = note
            } else {
                currentNotes.add(note)
            }
            _notes.value = currentNotes
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            val currentNotes = _notes.value.toMutableList()
            currentNotes.removeAll { it.id == note.id }
            _notes.value = currentNotes
        }
    }
}