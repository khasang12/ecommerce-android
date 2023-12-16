package com.example.ecommerce_app

import androidx.test.espresso.IdlingResource

class NetworkManager private constructor() {

    private var isNetworkIdle = true
    private var resourceCallback: IdlingResource.ResourceCallback? = null

    companion object {
        private val instance = NetworkManager()

        fun getInstance(): NetworkManager {
            return instance
        }
    }

    fun setNetworkIdleState(idle: Boolean) {
        isNetworkIdle = idle
        notifyIdleStateChanged()
    }

    fun isNetworkIdle(): Boolean {
        return isNetworkIdle
    }

    fun registerCallback(callback: IdlingResource.ResourceCallback) {
        resourceCallback = callback
    }

    fun unregisterCallback() {
        resourceCallback = null
    }

    private fun notifyIdleStateChanged() {
        resourceCallback?.onTransitionToIdle()
    }
}
