package com.example.notescompose.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.notescompose.model.Note
import com.example.notescompose.viewModel.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesListScreen(navController: NavController, notesViewModel: NotesViewModel) {
    val notes = notesViewModel.notes.collectAsState().value

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = { navController.navigate("edit_note") },
            containerColor = Color(0xff028090),
        ) {
            Text(
                "+", style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xffE4FDE1)
                )
            )
        }
    }, content = { paddingValues ->
        NotesContent(
            paddingValues = paddingValues, navController = navController, notes = notes
        )
    },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Sticky Notes",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xff028090)
                ),
            )
        })
}

@Composable
private fun NotesContent(
    paddingValues: PaddingValues, navController: NavController, notes: List<Note>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffE4FDE1))
            .padding(paddingValues)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(notes) { note ->
                NoteItem(note = note, onClick = { navController.navigate("edit_note/${note.id}") })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteItem(note: Note, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xff114B5F)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight()
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color(0xffF45B69),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = note.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis,
                color = Color(0xffE4FDE1)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotesListScreenPreview() {
    val mockNavController = rememberNavController()
    val mockNotesViewModel = NotesViewModel()
    mockNotesViewModel.addOrUpdateNote(Note(1, "Title","description"))

    NotesListScreen(navController = mockNavController, notesViewModel = mockNotesViewModel)
}