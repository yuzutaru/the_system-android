package com.thesystem.workout.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thesystem.core.designsystem.TheSystemColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen(
    modifier: Modifier = Modifier,
    viewModel: WorkoutViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = TheSystemColors.Background,
        topBar = { TopAppBar(title = { Text("Quest Log") }) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.logSampleWorkout() },
                containerColor = TheSystemColors.Accent,
            ) {
                Icon(Icons.Default.Add, contentDescription = "Log workout")
            }
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            state.lastResult?.let { result ->
                item {
                    Text(
                        text = "+${result.session.xpEarned} XP · +${result.session.goldEarned} gold",
                        color = TheSystemColors.Accent,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
            if (state.sessions.isEmpty() && !state.isLoading) {
                item {
                    Text(
                        text = "No workouts logged yet.",
                        color = TheSystemColors.TextSecondary,
                    )
                }
            }
            items(state.sessions, key = { it.id }) { session ->
                Card(colors = CardDefaults.cardColors(containerColor = TheSystemColors.Surface)) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(
                            text = session.questId ?: "Freestyle Session",
                            color = TheSystemColors.TextPrimary,
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Text(
                            text = "+${session.xpEarned} XP · +${session.goldEarned} gold",
                            color = TheSystemColors.Accent,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }
            }
        }
    }
}
