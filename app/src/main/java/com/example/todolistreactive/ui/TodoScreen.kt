package com.example.todolistreactive.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolistreactive.viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(vm: TodoViewModel = viewModel()) {
    val filteredTodos by vm.filteredTodos.collectAsState()
    val filter by vm.filter.collectAsState()
    val searchQuery by vm.searchQuery.collectAsState()

    var newTaskText by rememberSaveable { mutableStateOf("") }
    val (allCount, activeCount, doneCount) = vm.getCounts()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { vm.setSearchQuery(it) },
            label = { Text("Cari tugas...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            singleLine = true
        )


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            OutlinedTextField(
                value = newTaskText,
                onValueChange = { newTaskText = it },
                label = { Text("Tambah tugas...") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = {
                    if (newTaskText.isNotBlank()) {
                        vm.addTask(newTaskText.trim())
                        newTaskText = ""
                    }
                }
            ) {
                Text("Tambah")
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                FilterButton("all", "Semua 📝 $allCount ", filter, onClick = { vm.setFilter("all") })
                FilterButton("active", "Aktif ✅ $activeCount ", filter, onClick = { vm.setFilter("active") })
                FilterButton("done", "Selesai ⏳ $doneCount ", filter, onClick = { vm.setFilter("done") })
            }
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))


        if (filteredTodos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Belum ada tugas yang sesuai 🔎", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                items(filteredTodos) { todo ->
                    TodoItem(
                        todo = todo,
                        onToggle = { vm.toggleTask(todo.id) },
                        onDelete = { vm.deleteTask(todo.id) }
                    )
                }
            }
        }
    }
}
