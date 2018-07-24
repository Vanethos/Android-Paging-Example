package com.vanethos.example.presentation.utils

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Handles the content if it's never been handled.
     */
    fun handle(action: (T) -> Unit) {
        if (!hasBeenHandled) {
            action(content)
            hasBeenHandled = true
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peek(): T = content
}