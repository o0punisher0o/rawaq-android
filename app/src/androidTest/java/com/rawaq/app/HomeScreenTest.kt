package com.rawaq.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rawaq.app.core.theme.RawaqTheme
import com.rawaq.app.feature.today.TodayScreen
import com.rawaq.app.feature.today.TodayViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun todayScreen_displaysRawaqTitleAndEmptyState() {
        val viewModel = TodayViewModel()

        composeTestRule.setContent {
            RawaqTheme {
                TodayScreen(viewModel = viewModel)
            }
        }

        composeTestRule.onNodeWithText("Rawaq").assertIsDisplayed()
        composeTestRule.onNodeWithText("Your space is ready").assertIsDisplayed()
    }
}
