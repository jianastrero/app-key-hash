package dev.jianastrero.appkeyhash.flow

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SavableMutableStateFlow<T>(
    private val savedStateHandle: SavedStateHandle,
    private val key: String,
    defaultValue: T
) : MutableStateFlow<T> {
    private val _state: MutableStateFlow<T> = MutableStateFlow(savedStateHandle.get<T>(key) ?: defaultValue)
    override val replayCache: List<T>
        get() = _state.replayCache
    override val subscriptionCount: StateFlow<Int>
        get() = _state.subscriptionCount

    override var value: T
        get() = _state.value
        set(value) {
            _state.value = value
            savedStateHandle[key] = value
        }

    override suspend fun collect(collector: FlowCollector<T>): Nothing = _state.collect(collector)

    override fun compareAndSet(expect: T, update: T): Boolean = _state.compareAndSet(expect, update)

    @ExperimentalCoroutinesApi
    override fun resetReplayCache() = _state.resetReplayCache()

    override fun tryEmit(value: T): Boolean = _state.tryEmit(value)

    override suspend fun emit(value: T) = _state.emit(value)
}
