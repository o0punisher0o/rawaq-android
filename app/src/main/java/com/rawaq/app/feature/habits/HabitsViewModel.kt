package com.rawaq.app.feature.habits

import androidx.lifecycle.ViewModel
import com.rawaq.app.data.model.Habit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HabitsViewModel : ViewModel() {

    private val _habits = MutableStateFlow<List<Habit>>(emptyList())
    val habits: StateFlow<List<Habit>> = _habits.asStateFlow()

    fun addDemoHabit() {
        val current = _habits.value.toMutableList()
        val categories = listOf("Mindfulness", "Health", "Knowledge", "Discipline")
        val sampleTitles = listOf("Morning Azkar", "Read 10 pages", "Workout / Exercise", "Night Surah Al-Mulk")
        val index = current.size % sampleTitles.size

        current.add(
            Habit(
                id = System.currentTimeMillis().toString(),
                title = sampleTitles[index],
                category = categories[index % categories.size],
                isCompletedToday = false,
                streakDays = (1..5).random()
            )
        )
        _habits.value = current
    }

    fun toggleHabit(habitId: String) {
        _habits.value = _habits.value.map { habit ->
            if (habit.id == habitId) {
                habit.copy(isCompletedToday = !habit.isCompletedToday)
            } else {
                habit
            }
        }
    }
}
