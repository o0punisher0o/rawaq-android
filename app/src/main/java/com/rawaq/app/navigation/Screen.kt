package com.rawaq.app.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Today
import androidx.compose.ui.graphics.vector.ImageVector
import com.rawaq.app.R

sealed class Screen(
    val route: String,
    @StringRes val titleRes: Int,
    val icon: ImageVector
) {
    object Today : Screen("today", R.string.nav_today, Icons.Outlined.Today)
    object Habits : Screen("habits", R.string.nav_habits, Icons.Outlined.CheckCircle)
    object Goals : Screen("goals", R.string.nav_goals, Icons.Outlined.Flag)
    object Settings : Screen("settings", R.string.nav_settings, Icons.Outlined.Settings)

    companion object {
        val bottomNavItems = listOf(Today, Habits, Goals, Settings)
    }
}
