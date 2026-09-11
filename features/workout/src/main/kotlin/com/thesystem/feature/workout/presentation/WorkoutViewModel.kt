package com.thesystem.feature.workout.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesystem.core.domain.ExerciseKind
import com.thesystem.core.domain.SessionEntry
import com.thesystem.core.domain.SetEntry
import com.thesystem.core.domain.WorkoutRepository
import com.thesystem.core.domain.WorkoutSession
import com.thesystem.feature.workout.LogResult
import com.thesystem.feature.workout.LogWorkoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Instant
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class WorkoutUiState(
    val sessions: List<WorkoutSession> = emptyList(),
    val lastResult: LogResult? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val logWorkout: LogWorkoutUseCase,
    private val repository: WorkoutRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(WorkoutUiState())
    val uiState: StateFlow<WorkoutUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            runCatching { repository.allSessions() }
                .onSuccess { sessions ->
                    _uiState.update { it.copy(sessions = sessions, isLoading = false) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(error = error.message, isLoading = false) }
                }
        }
    }

    fun logSampleWorkout() {
        viewModelScope.launch {
            val entries = listOf(
                SessionEntry(
                    id = UUID.randomUUID().toString(),
                    exerciseId = "barbell-squat",
                    kind = ExerciseKind.STRENGTH,
                    sets = listOf(
                        SetEntry(reps = 5, weightKg = 100.0),
                        SetEntry(reps = 5, weightKg = 100.0),
                        SetEntry(reps = 5, weightKg = 100.0),
                    ),
                ),
                SessionEntry(
                    id = UUID.randomUUID().toString(),
                    exerciseId = "run",
                    kind = ExerciseKind.CARDIO,
                    durationMin = 20.0,
                    distanceKm = 3.2,
                    avgHeartRate = 150,
                ),
            )
            runCatching { logWorkout(entries = entries, endedAt = Instant.now()) }
                .onSuccess { result ->
                    _uiState.update { it.copy(lastResult = result) }
                    load()
                }
                .onFailure { error ->
                    _uiState.update { it.copy(error = error.message) }
                }
        }
    }
}
