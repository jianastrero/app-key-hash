package dev.jianastrero.appkeyhash.ui.enumeration

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String) {

    open val arguments: List<NamedNavArgument> = emptyList()
    val fullRoute: String
        get() {
            return if (arguments.isEmpty()) {
                route
            } else {
                "$route/${arguments.joinToString("/") { "{${it.name}}" }}"
            }
        }

    data object Main : Screen("main")

    data object AppDetails : Screen("app_details") {

        const val PACKAGE_NAME = "package_name"

        override val arguments: List<NamedNavArgument> = listOf(
            navArgument(PACKAGE_NAME) {
                type = NavType.StringType
                defaultValue = ""
            }
        )
    }
}
