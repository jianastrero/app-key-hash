package dev.jianastrero.appkeyhash.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppDetailScreen(
    packageName: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(text = "App Detail Screen")
        Text(text = "Package Name: $packageName")
    }
}
