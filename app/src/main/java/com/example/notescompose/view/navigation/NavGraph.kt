package com.example.notescompose.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.notescompose.view.screens.NoteDetailsScreen
import com.example.notescompose.view.screens.NotesListScreen
import com.example.notescompose.viewModel.NotesViewModel

@Composable
fun NotesNavGraph(
    navController: NavHostController,
    notesViewModel: NotesViewModel
) {
    NavHost(navController = navController, startDestination = "notes_list") {
        composable("notes_list") {
            NotesListScreen(navController = navController, notesViewModel = notesViewModel)
        }
        composable("edit_note/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull()
            NoteDetailsScreen(
                navController = navController,
                noteId = noteId,
                notesViewModel = notesViewModel
            )
        }
        composable("edit_note") {
            NoteDetailsScreen(
                navController = navController,
                noteId = null,
                notesViewModel = notesViewModel
            )
        }
    }
}