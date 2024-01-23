package dev.jianastrero.appkeyhash.state

import dev.jianastrero.appkeyhash.ui.enumeration.LoadState
import dev.jianastrero.appkeyhash.model.App

data class MainState(
    override val loadState: LoadState = LoadState.Initial,
    val apps: List<App> = emptyList(),
) : ILoadableState
