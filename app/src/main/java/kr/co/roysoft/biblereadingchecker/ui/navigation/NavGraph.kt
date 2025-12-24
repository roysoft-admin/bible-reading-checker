package kr.co.roysoft.biblereadingchecker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kr.co.roysoft.biblereadingchecker.data.model.ReadingPlanType
import kr.co.roysoft.biblereadingchecker.ui.screen.*
import kr.co.roysoft.biblereadingchecker.ui.viewmodel.ReadingViewModel

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Home : Screen("home")
    object ReadingPlan : Screen("reading_plan")
    object Progress : Screen("progress")
    object Settings : Screen("settings")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: ReadingViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    
    NavHost(
        navController = navController,
        startDestination = if (uiState.selectedPlan != null) Screen.Home.route else Screen.Welcome.route
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onPlanSelected = { planType ->
                    viewModel.initializePlan(planType)
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Home.route) {
            HomeScreen(
                todayReading = uiState.todayReading?.passage,
                dayNumber = uiState.todayReading?.day ?: 0,
                isRead = uiState.todayReading?.completed ?: false,
                overallProgress = if (uiState.totalDays > 0) {
                    uiState.completedCount.toFloat() / uiState.totalDays.toFloat()
                } else 0f,
                currentStreak = uiState.currentStreak,
                isPremium = uiState.isPremium,
                onMarkAsRead = { completed ->
                    uiState.todayReading?.let {
                        viewModel.markAsRead(it.day, completed)
                    }
                },
                onNavigateToPlan = { navController.navigate(Screen.ReadingPlan.route) },
                onNavigateToProgress = { navController.navigate(Screen.Progress.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }
        
        composable(Screen.ReadingPlan.route) {
            val planName = ReadingPlanType.values()
                .find { it.id == uiState.selectedPlan }?.displayName ?: "Reading Plan"
            
            ReadingPlanScreen(
                readings = uiState.allReadings,
                planName = planName,
                isPremium = uiState.isPremium,
                onBack = { navController.popBackStack() },
                onReadingClick = { day ->
                    val reading = uiState.allReadings.find { it.day == day }
                    reading?.let {
                        viewModel.markAsRead(it.day, !it.completed)
                    }
                }
            )
        }
        
        composable(Screen.Progress.route) {
            val monthlyProgress = listOf(
                kr.co.roysoft.biblereadingchecker.ui.screen.MonthlyProgress("January", 12, 31),
                kr.co.roysoft.biblereadingchecker.ui.screen.MonthlyProgress("December", 28, 31),
                kr.co.roysoft.biblereadingchecker.ui.screen.MonthlyProgress("November", 30, 30)
            )
            
            ProgressScreen(
                overallProgress = if (uiState.totalDays > 0) {
                    uiState.completedCount.toFloat() / uiState.totalDays.toFloat()
                } else 0f,
                daysCompleted = uiState.completedCount,
                currentStreak = uiState.currentStreak,
                daysRemaining = uiState.totalDays - uiState.completedCount,
                monthlyProgress = monthlyProgress,
                isPremium = uiState.isPremium,
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Settings.route) {
            val planName = ReadingPlanType.values()
                .find { it.id == uiState.selectedPlan }?.displayName ?: "No plan selected"
            
            SettingsScreen(
                currentPlan = planName,
                reminderTime = uiState.reminderTime,
                isPremium = uiState.isPremium,
                onBack = { navController.popBackStack() },
                onPlanClick = { /* TODO: Show plan selection dialog */ },
                onReminderClick = { /* TODO: Show time picker */ },
                onPremiumClick = { viewModel.setIsPremium(true) },
                onAboutClick = { /* TODO: Show about dialog */ },
                onPrivacyClick = { /* TODO: Show privacy policy */ },
                onShareClick = { /* TODO: Share app */ }
            )
        }
    }
}

