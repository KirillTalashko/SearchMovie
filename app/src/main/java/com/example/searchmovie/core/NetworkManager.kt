package com.example.searchmovie.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.MutableLiveData


class NetworkManager(context: Context) : NetworkCallback() {

    private val stateNetwork = MutableLiveData<Boolean>()

    private val networkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
        .build()

    private val networks = ArrayList<Network>()

    private val manager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isConnect(): Boolean {
        var isConnected = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            manager.registerNetworkCallback(networkRequest, this)
            networks.forEach { network ->
                val capabilities = manager.getNetworkCapabilities(network)
                if (capabilities != null) {
                    if (capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                        isConnected = true
                        return@forEach
                    }
                }
            }
            stateNetwork.postValue(isConnected)
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
            stateNetwork.postValue(isConnected)
        }
        return stateNetwork.value ?: false
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        this.networks.add(network)
        stateNetwork.postValue(true)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        this.networks.remove(network)
        stateNetwork.postValue(false)
    }

    override fun onUnavailable() {
        super.onUnavailable()
        manager.unregisterNetworkCallback(this)
    }
}
