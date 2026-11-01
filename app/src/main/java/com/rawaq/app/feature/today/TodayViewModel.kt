package com.rawaq.app.feature.today

import androidx.lifecycle.ViewModel
import com.rawaq.app.data.model.DailyProgress
import com.rawaq.app.data.model.Goal
import com.rawaq.app.data.model.Habit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class TodayUiState(
    val currentDateText: String = "",
    val progress: DailyProgress = DailyProgress(0, 0),
    val habits: List<Habit> = emptyList(),
    val goals: List<Goal> = emptyList()
)

class TodayViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TodayUiState())
    val uiState: StateFlow<TodayUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        val dateFormat = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault())
        val dateString = dateFormat.format(Date())

        // Initial state is clean/empty to allow user to experience the minimal space.
        _uiState.value = TodayUiState(
            currentDateText = dateString,
            progress = DailyProgress(completedCount = 0, totalCount = 0),
            habits = emptyList(),
            goals = emptyList()
        )
    }

    fun addDemoHabit() {
        val currentHabits = _uiState.value.habits.toMutableList()
        val newHabit = Habit(
            id = System.currentTimeMillis().toString(),
            title = "Daily Reading (15 mins)",
            category = "Personal Growth",
            isCompletedToday = false,
            streakDays = 1
        )
        currentHabits.add(newHabit)
        updateStateWithHabits(currentHabits)
    }

    fun toggleHabit(habitId: String) {
        val updatedHabits = _uiState.value.habits.map { habit ->
            if (habit.id == habitId) {
                habit.copy(isCompletedToday = !habit.isCompletedToday)
            } else {
                habit
            }
        }
        updateStateWithHabits(updatedHabits)
    }

    private fun updateStateWithHabits(habits: List<Habit>) {
        val completed = habits.count { it.isCompletedToday }
        _uiState.value = _uiState.value.copy(
            habits = habits,
            progress = DailyProgress(completedCount = completed, totalCount = habits.size)
        )
    }
}
