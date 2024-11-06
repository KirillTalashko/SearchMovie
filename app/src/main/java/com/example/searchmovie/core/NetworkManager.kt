package com.example.searchmovie.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.MutableLiveData


class NetworkManager(context: Context) : NetworkCallback() {

    private val _stateNetwork = MutableLiveData<Boolean>()
    private val manager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isConnect(): Boolean {
        var isConnected = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val network = manager.activeNetwork
            val capabilities = manager.getNetworkCapabilities(network)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (capabilities != null) {
                    isConnected = when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
                _stateNetwork.postValue(isConnected)
            }
        } else {
            manager.registerDefaultNetworkCallback(this)
            manager.allNetworks.forEach { network ->
                val capabilities = manager.getNetworkCapabilities(network)
                if (capabilities != null) {
                    if (capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                        isConnected = true
                        return@forEach
                    }
                }
            }
            _stateNetwork.postValue(isConnected)
        }
        return _stateNetwork.value ?: false
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        _stateNetwork.postValue(true)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        _stateNetwork.postValue(false)
    }
}
