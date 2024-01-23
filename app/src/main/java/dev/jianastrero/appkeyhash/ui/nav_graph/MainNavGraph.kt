package dev.jianastrero.appkeyhash.ui.nav_graph

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.jianastrero.appkeyhash.ui.enumeration.Screen
import dev.jianastrero.appkeyhash.ui.screen.MainScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    composable(Screen.Main.route) {
        MainScreen(modifier = modifier)
    }
}
