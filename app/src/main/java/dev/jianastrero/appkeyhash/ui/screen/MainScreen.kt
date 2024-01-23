package dev.jianastrero.appkeyhash.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.jianastrero.appkeyhash.ui.component.AppItem
import dev.jianastrero.appkeyhash.ui.enumeration.LoadState
import dev.jianastrero.appkeyhash.ui.theme.AppKeyHashTheme
import dev.jianastrero.appkeyhash.viewmodel.MainViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    var searchText: String by rememberSaveable { mutableStateOf("") }
    val state by viewModel.state.collectAsState()
    val filteredApps by remember(searchText, state) {
        derivedStateOf {
            state.apps.filter { app ->
                app.appName.contains(searchText, ignoreCase = true)
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        modifier = modifier
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text(text = "Search") },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        )
        AnimatedContent(
            targetState = state.loadState,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = "AnimatedContent -> allApps",
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { loadState ->
            when (loadState) {
                LoadState.Initial,
                LoadState.Loading -> Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                    Text(
                        text = "Loading"
                    )
                }
                LoadState.Error,
                LoadState.Empty -> Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "No apps found"
                    )
                }
                LoadState.Loaded -> LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(filteredApps) { app ->
                        AppItem(
                            app = app,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    AppKeyHashTheme(modifier = Modifier.fillMaxSize()) {
        MainScreen()
    }
}
