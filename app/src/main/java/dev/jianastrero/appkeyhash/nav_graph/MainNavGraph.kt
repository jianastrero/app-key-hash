package dev.jianastrero.appkeyhash.nav_graph

import android.util.Log
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.jianastrero.appkeyhash.ui.enumeration.Screen
import dev.jianastrero.appkeyhash.ui.screen.AppDetailScreen
import dev.jianastrero.appkeyhash.ui.screen.MainScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    composable(Screen.Main.fullRoute) {
        MainScreen(
            onAppClick = { packageName ->
                Log.d("JIANDDEBUG", "packageName: $packageName")
                Log.d("JIANDDEBUG", "fullRoute: ${Screen.AppDetails.fullRoute}")
                Log.d("JIANDDEBUG", "route: ${Screen.AppDetails.route}/$packageName")
                navController.navigate("${Screen.AppDetails.route}/$packageName")
            },
            modifier = modifier
        )
    }
    composable(
        Screen.AppDetails.fullRoute,
        arguments = Screen.AppDetails.arguments
    ) { backStackEntry ->
        val packageName = backStackEntry.arguments?.getString(Screen.AppDetails.PACKAGE_NAME).orEmpty()

        AppDetailScreen(
            onBackPress = {
                navController.popBackStack()
            },
            packageName = packageName,
            modifier = modifier
        )
    }
}
