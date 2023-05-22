package com.example.common.extenssion

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

inline fun <T> Flow<T>.collectWhileStarted(
    lifecycleOwner: LifecycleOwner,
    crossinline action: suspend (value: T) -> Unit
) {
    var job: Job? = null
    lifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { source, event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                job = source.lifecycleScope.launch {
                    collect {
                        action(it)
                    }
                }
            }
            Lifecycle.Event.ON_STOP -> {
                job?.cancel()
                job = null
            }
            else -> {
            }
        }
    })
}
