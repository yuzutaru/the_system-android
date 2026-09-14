package com.thesystem.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.thesystem.core.designsystem.TheSystemColors
import com.thesystem.core.navigation.CharacterRoute
import com.thesystem.core.navigation.HomeRoute
import com.thesystem.core.navigation.QuestRoute
import com.thesystem.core.navigation.StatsRoute
import com.thesystem.core.navigation.WorkoutRoute
import com.thesystem.character.CharacterScreen
import com.thesystem.home.HomeScreen
import com.thesystem.quest.QuestScreen
import com.thesystem.stats.StatsScreen
import com.thesystem.workout.presentation.WorkoutScreen
import kotlin.reflect.KClass

private data class TopLevelDestination(
    val route: Any,
    val routeClass: KClass<*>,
    val label: String,
    val icon: ImageVector,
)

private val destinations = listOf(
    TopLevelDestination(HomeRoute, HomeRoute::class, "Home", Icons.Default.Home),
    TopLevelDestination(WorkoutRoute, WorkoutRoute::class, "Log", Icons.AutoMirrored.Filled.List),
    TopLevelDestination(QuestRoute, QuestRoute::class, "Quests", Icons.Default.Star),
    TopLevelDestination(StatsRoute, StatsRoute::class, "Stats", Icons.Default.Favorite),
    TopLevelDestination(CharacterRoute, CharacterRoute::class, "Hero", Icons.Default.Person),
)

@Composable
fun AppRoot(viewModel: AppViewModel = hiltViewModel()) {
    val character by viewModel.character.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    Scaffold(
        containerColor = TheSystemColors.Background,
        bottomBar = {
            NavigationBar(containerColor = TheSystemColors.Surface) {
                destinations.forEach { destination ->
                    val selected = currentDestination?.hierarchy?.any {
                        it.hasRoute(destination.routeClass)
                    } == true
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigateToTab(destination.route)
                        },
                        icon = { Icon(destination.icon, contentDescription = destination.label) },
                        label = { Text(destination.label) },
                    )
                }
            }
        },
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = Modifier.padding(padding),
        ) {
            composable<HomeRoute> {
                HomeScreen(
                    onLogWorkout = { navController.navigateToTab(WorkoutRoute) },
                    onSeeStats = { navController.navigateToTab(StatsRoute) },
                    onBuildCharacter = { navController.navigateToTab(CharacterRoute) },
                )
            }
            composable<WorkoutRoute> { WorkoutScreen() }
            composable<QuestRoute> { QuestScreen() }
            composable<StatsRoute> { StatsScreen(character = character) }
            composable<CharacterRoute> { CharacterScreen(character = character) }
        }
    }
}

private fun NavHostController.navigateToTab(route: Any) {
    navigate(route) {
        popUpTo(graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
