package dev.jianastrero.appkeyhash.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jianastrero.appkeyhash.ext.launchIO
import dev.jianastrero.appkeyhash.flow.SavableMutableStateFlow
import dev.jianastrero.appkeyhash.state.AppDetailState
import dev.jianastrero.appkeyhash.ui.enumeration.LoadState
import dev.jianastrero.appkeyhash.use_case.GetAppKeyHashUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class AppDetailViewModel @Inject constructor(
    savedState: SavedStateHandle,
    private val getAppKeyHashUseCase: GetAppKeyHashUseCase
) : ViewModel() {

    private val _state = SavableMutableStateFlow(
        savedStateHandle = savedState,
        key = "state",
        defaultValue = AppDetailState()
    )
    val state = _state.asStateFlow()

    fun init(packageName: String) = launchIO {
        updateState(
            state.value.copy(
                loadState = LoadState.Loading,
                packageName = packageName
            )
        )

        val hashKey = getAppKeyHashUseCase(packageName)

        updateState(
            state.value.copy(
                loadState = if (hashKey == null) LoadState.Error else LoadState.Loaded,
                hashKey = hashKey
            )
        )
    }

    fun updateState(state: AppDetailState) = launchIO {
        _state.emit(state)
    }
}
