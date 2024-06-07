package com.example.notescompose.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.notescompose.view.navigation.NotesNavGraph
import com.example.notescompose.viewModel.NotesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val notesViewModel: NotesViewModel by viewModels()
            AppContent(notesViewModel)
        }
    }
}

@Composable
fun AppContent(notesViewModel: NotesViewModel = viewModel()) {
    val navController = rememberNavController()

    NotesNavGraph(
        navController = navController,
        notesViewModel = notesViewModel
    )
}
