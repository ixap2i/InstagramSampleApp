package com.ixap2i.floap

import kotlinx.coroutines.channels.BroadcastChannel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.withContext

@Suppress("EXPERIMENTAL_API_USAGE")
class Dispatcher {
    private val _actions = BroadcastChannel<Action>(Channel.CONFLATED)
    val events: ReceiveChannel<Action> get() = _actions.openSubscription()
    private val context = newFixedThreadPoolContext(1, "Dispatcher")
    private val scope = CoroutineScope(context)

    inline fun <reified T : Action> subscribe(): ReceiveChannel<T> {
        return GlobalScope.produce(Dispatchers.Unconfined, Channel.UNLIMITED) {
            events.consumeEach { action ->
                (action as? T)?.let { send(it) }
            }
        }
    }

    suspend fun dispatch(action: Action) {
        withContext(context) {
            _actions.send(action)
        }
    }

    fun launchAndDispatch(action: Action) {
        scope.launch {
            _actions.send(action)
        }
    }
}

enum class LoadingState {
    INITIALIZED, LOADING, LOADED, FAILED;

    val isInitialized: Boolean get() = this == INITIALIZED
    val isLoading get() = this == LOADING
    val isLoaded: Boolean get() = this == LOADED
    val isFailed: Boolean get() = this == FAILED
}

open class Action
