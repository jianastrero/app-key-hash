package dev.jianastrero.appkeyhash.ui.enumeration

sealed class Screen(val route: String) {
    data object Main : Screen("main")
    data object AppDetails : Screen("app_details")
}
