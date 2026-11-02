package com.rawaq.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.rawaq.app.core.theme.RawaqTheme
import com.rawaq.app.feature.goals.GoalsViewModel
import com.rawaq.app.feature.habits.HabitsViewModel
import com.rawaq.app.feature.settings.SettingsViewModel
import com.rawaq.app.feature.today.TodayViewModel
import com.rawaq.app.navigation.RawaqBottomBar
import com.rawaq.app.navigation.RawaqNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val todayViewModel: TodayViewModel by viewModels()
    private val habitsViewModel: HabitsViewModel by viewModels()
    private val goalsViewModel: GoalsViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var darkThemeState by remember { mutableStateOf(false) }

            RawaqTheme(darkTheme = darkThemeState) {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { RawaqBottomBar(navController = navController) }
                ) { innerPadding ->
                    RawaqNavGraph(
                        navController = navController,
                        todayViewModel = todayViewModel,
                        habitsViewModel = habitsViewModel,
                        goalsViewModel = goalsViewModel,
                        settingsViewModel = settingsViewModel,
                        onDarkThemeToggle = { darkThemeState = it },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
