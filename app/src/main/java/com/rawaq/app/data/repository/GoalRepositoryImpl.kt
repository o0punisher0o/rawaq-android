package com.rawaq.app.data.repository

import com.rawaq.app.data.local.dao.GoalDao
import com.rawaq.app.data.local.mapper.toDomain
import com.rawaq.app.data.local.mapper.toEntity
import com.rawaq.app.domain.model.Goal
import com.rawaq.app.domain.model.GoalStatus
import com.rawaq.app.domain.repository.GoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalRepositoryImpl @Inject constructor(
    private val goalDao: GoalDao
) : GoalRepository {

    override suspend fun createGoal(goal: Goal) {
        goalDao.insertGoal(goal.toEntity())
    }

    override suspend fun updateGoal(goal: Goal) {
        goalDao.updateGoal(goal.toEntity().copy(updatedAtTimestamp = System.currentTimeMillis()))
    }

    override fun observeGoals(): Flow<List<Goal>> {
        return goalDao.observeAllGoals().map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun observeGoalsByStatus(status: GoalStatus): Flow<List<Goal>> {
        return goalDao.observeGoalsByStatus(status.name).map { list ->
            list.map { it.toDomain() }
        }
    }
}
