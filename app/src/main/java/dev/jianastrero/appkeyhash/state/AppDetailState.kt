package dev.jianastrero.appkeyhash.state

import dev.jianastrero.appkeyhash.ui.enumeration.LoadState

data class AppDetailState(
    override val loadState: LoadState = LoadState.Initial,
    val packageName: String = "",
    val hashKey: String? = null,
) : ILoadableState
