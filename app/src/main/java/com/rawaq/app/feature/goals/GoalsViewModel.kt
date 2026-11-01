package com.rawaq.app.feature.goals

import androidx.lifecycle.ViewModel
import com.rawaq.app.data.model.Goal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GoalsViewModel : ViewModel() {

    private val _goals = MutableStateFlow<List<Goal>>(emptyList())
    val goals: StateFlow<List<Goal>> = _goals.asStateFlow()

    fun addDemoGoal() {
        val current = _goals.value.toMutableList()
        val sampleGoals = listOf(
            Goal("1", "Read 5 Books This Year", "Consistent reading every evening", 0.40f, "Dec 2025"),
            Goal("2", "Memorize Juz Amma", "Review 1 page daily with tajweed", 0.65f, "Nov 2025"),
            Goal("3", "Run 5K Marathon", "Progressive training 3 times a week", 0.25f, "Oct 2025")
        )
        val nextGoal = sampleGoals[current.size % sampleGoals.size].copy(
            id = System.currentTimeMillis().toString()
        )
        current.add(nextGoal)
        _goals.value = current
    }
}
