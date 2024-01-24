package dev.jianastrero.appkeyhash.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.jianastrero.appkeyhash.viewmodel.AppDetailViewModel

@Composable
fun AppDetailScreen(
    onBackPress: () -> Unit,
    packageName: String,
    modifier: Modifier = Modifier,
    viewModel: AppDetailViewModel = hiltViewModel()
) {
    val clipboardManager = LocalClipboardManager.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(packageName) {
        viewModel.init(packageName)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        IconButton(
            onClick = onBackPress,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
        }
        Text(
            text = state.packageName,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        state.hashKey?.let {
            Text(
                text = it,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        } ?: run {
            Text(
                text = "Couldn't get this app's hash key",
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(
            modifier = Modifier
                .clickable {
                    state.hashKey?.also { hashKey ->
                        clipboardManager.setText(
                            buildAnnotatedString {
                                 append(hashKey)
                            }
                        )
                    }
                }
                .padding(12.dp)
        ) {
            Icon(imageVector = Icons.Rounded.ContentCopy, contentDescription = null)
            Text(
                text = "Copy to clipboard",
            )
        }
    }
}

@Preview
@Composable
private fun AppDetailScreenPreview() {
    AppDetailScreen(
        onBackPress = {},
        packageName = ""
    )
}
