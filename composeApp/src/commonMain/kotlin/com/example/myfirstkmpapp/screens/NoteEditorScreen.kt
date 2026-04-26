package com.example.myfirstkmpapp.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myfirstkmpapp.viewmodel.NotesViewModel

@Composable
fun NoteEditorScreen(
    noteId: Long?,
    viewModel: NotesViewModel,
    onBack: () -> Unit
) {
    val selectedNote by viewModel.selectedNote.collectAsState()
    var title by remember(noteId) { mutableStateOf("") }
    var content by remember(noteId) { mutableStateOf("") }

    LaunchedEffect(noteId) {
        if (noteId != null) viewModel.selectNote(noteId)
        else {
            viewModel.clearSelectedNote()
            title = ""; content = ""
        }
    }

    LaunchedEffect(selectedNote) {
        selectedNote?.let { title = it.title; content = it.content }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(if (noteId == null) "New Note" else "Edit Note", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = content, onValueChange = { content = it }, label = { Text("Content") }, modifier = Modifier.fillMaxWidth().weight(1f))
        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                if (noteId == null) viewModel.addNote(title, content)
                else viewModel.updateNote(noteId, title, content)
                onBack()
            }, modifier = Modifier.weight(1f)) { Text("Save") }
            OutlinedButton(onClick = onBack, modifier = Modifier.weight(1f)) { Text("Cancel") }
        }
    }
}