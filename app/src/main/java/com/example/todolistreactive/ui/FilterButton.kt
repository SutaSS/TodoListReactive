package com.example.todolistreactive.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FilterButton(value: String, label: String, current: String, onClick: () -> Unit) {
    val isSelected = value == current
    val colors = ButtonDefaults.buttonColors(
        containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
    )

    Button(
        onClick = onClick,
        colors = colors,
        modifier = Modifier.padding(end = 4.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
    }
}