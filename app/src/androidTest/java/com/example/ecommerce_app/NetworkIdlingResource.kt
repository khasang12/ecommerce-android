package com.example.ecommerce_app

import androidx.test.espresso.IdlingResource

class NetworkIdlingResource : IdlingResource {

    private val networkManager = NetworkManager.getInstance()
    private var callback: IdlingResource.ResourceCallback? = null

    override fun getName(): String {
        return "NetworkIdlingResource"
    }

    override fun isIdleNow(): Boolean {
        return networkManager.isNetworkIdle()
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        this.callback = callback
        networkManager.registerCallback(callback)
    }

    fun unregisterCallback() {
        callback?.let { networkManager.unregisterCallback() }
    }
}

