package com.rawaq.app

import com.rawaq.app.feature.today.TodayViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TodayViewModelTest {

    private lateinit var viewModel: TodayViewModel

    @Before
    fun setUp() {
        viewModel = TodayViewModel()
    }

    @Test
    fun `initial state is empty`() {
        val state = viewModel.uiState.value
        assertTrue(state.habits.isEmpty())
        assertTrue(state.goals.isEmpty())
        assertEquals(0, state.progress.totalCount)
    }

    @Test
    fun `addDemoHabit increases habit count and updates progress`() {
        viewModel.addDemoHabit()
        val state = viewModel.uiState.value

        assertEquals(1, state.habits.size)
        assertEquals(1, state.progress.totalCount)
        assertEquals(0, state.progress.completedCount)
        assertFalse(state.habits.first().isCompletedToday)
    }

    @Test
    fun `toggleHabit updates completion state and progress`() {
        viewModel.addDemoHabit()
        val habitId = viewModel.uiState.value.habits.first().id

        viewModel.toggleHabit(habitId)

        val state = viewModel.uiState.value
        assertTrue(state.habits.first().isCompletedToday)
        assertEquals(1, state.progress.completedCount)
    }
}
