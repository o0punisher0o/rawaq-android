package com.rawaq.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rawaq.app.feature.goals.GoalsScreen
import com.rawaq.app.feature.goals.GoalsViewModel
import com.rawaq.app.feature.habits.HabitsScreen
import com.rawaq.app.feature.habits.HabitsViewModel
import com.rawaq.app.feature.settings.SettingsScreen
import com.rawaq.app.feature.settings.SettingsViewModel
import com.rawaq.app.feature.today.TodayScreen
import com.rawaq.app.feature.today.TodayViewModel

@Composable
fun RawaqNavGraph(
    navController: NavHostController,
    todayViewModel: TodayViewModel,
    habitsViewModel: HabitsViewModel,
    goalsViewModel: GoalsViewModel,
    settingsViewModel: SettingsViewModel,
    onDarkThemeToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Today.route,
        modifier = modifier
    ) {
        composable(Screen.Today.route) {
            TodayScreen(viewModel = todayViewModel)
        }
        composable(Screen.Habits.route) {
            HabitsScreen(viewModel = habitsViewModel)
        }
        composable(Screen.Goals.route) {
            GoalsScreen(viewModel = goalsViewModel)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                viewModel = settingsViewModel,
                onDarkThemeToggle = onDarkThemeToggle
            )
        }
    }
}
