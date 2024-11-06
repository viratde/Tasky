package com.tasky.agenda.data.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.tasky.agenda.domain.utils.ConnectivityObserver
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class NetworkConnectivityObserver(
    context: Context
) : ConnectivityObserver {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    
    override fun observe(): Flow<Boolean> {
        return callbackFlow { 
            
            val observer = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(true) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(false) }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch { send(false) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(false) }
                }
            }

            connectivityManager.registerDefaultNetworkCallback(observer)
            awaitClose {
                connectivityManager.unregisterNetworkCallback(observer)
            }
        }.distinctUntilChanged()
    }
}