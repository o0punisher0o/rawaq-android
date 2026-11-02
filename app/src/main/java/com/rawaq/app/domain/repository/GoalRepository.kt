package com.rawaq.app.domain.repository

import com.rawaq.app.domain.model.Goal
import com.rawaq.app.domain.model.GoalStatus
import kotlinx.coroutines.flow.Flow

interface GoalRepository {
    suspend fun createGoal(goal: Goal)
    suspend fun updateGoal(goal: Goal)
    fun observeGoals(): Flow<List<Goal>>
    fun observeGoalsByStatus(status: GoalStatus): Flow<List<Goal>>
}
