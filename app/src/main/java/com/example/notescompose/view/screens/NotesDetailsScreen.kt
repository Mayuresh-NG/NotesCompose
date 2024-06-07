package com.example.notescompose.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notescompose.model.Note
import com.example.notescompose.viewModel.NotesViewModel

@Composable
fun NoteDetailsScreen(
    navController: NavController,
    noteId: Int?,
    notesViewModel: NotesViewModel
) {
    val notes = notesViewModel.notes.collectAsState().value
    val existingNote = notes.find { it.id == noteId }
    var title by remember { mutableStateOf(existingNote?.title ?: "") }
    var description by remember { mutableStateOf(existingNote?.description ?: "") }
    var isSaveButtonEnabled by remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.fillMaxSize(), content = { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = Color(0xff114B5F))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                TextField(
                    value = title,
                    onValueChange = {
                        title = it
                        isSaveButtonEnabled = it.isNotEmpty() && description.isNotEmpty()
                    },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(Color(0xffF45B69))
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = description,
                    onValueChange = {
                        description = it
                        isSaveButtonEnabled = it.isNotEmpty() && title.isNotEmpty()
                    },
                    label = { Text("Description") },
                    colors = TextFieldDefaults.colors(Color(0xff456990)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(15f)
                        .verticalScroll(rememberScrollState())
                )
                Spacer(modifier = Modifier.weight(1f))
                if (noteId != null) {
                    Button(
                        onClick = {
                            notesViewModel.deleteNote(existingNote ?: return@Button)
                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xffF45B69)),
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier
                            .align(Alignment.End)
                            .fillMaxWidth()
                    ) {
                        Text("Delete")
                    }
                }
                Button(
                    onClick = {
                        notesViewModel.addOrUpdateNote(
                            Note(
                                id = noteId ?: (notes.size + 1),
                                title = title,
                                description = description
                            )
                        )
                        navController.popBackStack()
                    },
                    enabled = isSaveButtonEnabled,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xff456990)),
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .align(Alignment.End)
                        .fillMaxWidth()
                ) {
                    Text("Save")
                }
            }
        }
    })
}