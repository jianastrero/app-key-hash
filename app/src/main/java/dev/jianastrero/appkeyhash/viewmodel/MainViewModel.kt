package dev.jianastrero.appkeyhash.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jianastrero.appkeyhash.ext.launchIO
import dev.jianastrero.appkeyhash.flow.SavableMutableStateFlow
import dev.jianastrero.appkeyhash.state.MainState
import dev.jianastrero.appkeyhash.ui.enumeration.LoadState
import dev.jianastrero.appkeyhash.use_case.GetAllAppsUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class MainViewModel @Inject constructor(
    savedState: SavedStateHandle,
    private val getAllAppsUseCase: GetAllAppsUseCase,
) : ViewModel() {

    private val _state = SavableMutableStateFlow(
        savedStateHandle = savedState,
        key = "main_state",
        defaultValue = MainState()
    )
    val state = _state.asStateFlow()

    init {
        if (state.value.loadState == LoadState.Initial) {
            init()
        }
    }

    private fun init() = launchIO {
        updateState(state.value.copy(loadState = LoadState.Loading))

        val apps = getAllAppsUseCase()

        updateState(
            state.value.copy(
                loadState = if (apps.isEmpty()) LoadState.Empty else LoadState.Loaded,
                apps = apps
            )
        )
    }

    fun updateState(state: MainState) = launchIO {
        _state.emit(state)
    }
}
