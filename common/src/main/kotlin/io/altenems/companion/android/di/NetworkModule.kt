package io.altenems.companion.android.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import androidx.core.content.getSystemService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.altenems.companion.android.common.data.network.NetworkChangeObserver
import io.altenems.companion.android.common.data.network.NetworkChangeObserverImpl
import io.altenems.companion.android.common.data.network.NetworkHelper
import io.altenems.companion.android.common.data.network.NetworkHelperImpl
import io.altenems.companion.android.common.data.network.NetworkStatusMonitor
import io.altenems.companion.android.common.data.network.NetworkStatusMonitorImpl
import io.altenems.companion.android.common.data.network.WifiHelper
import io.altenems.companion.android.common.data.network.WifiHelperImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NetworkModule {

    companion object {
        @Provides
        @Singleton
        fun provideConnectivityManager(@ApplicationContext appContext: Context) =
            checkNotNull(appContext.getSystemService<ConnectivityManager>()) {
                "ConnectivityManager is not available on this device"
            }

        @Provides
        @Singleton
        fun provideWifiManager(@ApplicationContext appContext: Context): WifiManager? =
            appContext.getSystemService<WifiManager>()
    }

    @Binds
    @Singleton
    abstract fun bindWifiHelper(wifiHelper: WifiHelperImpl): WifiHelper

    @Binds
    @Singleton
    abstract fun bindNetworkHelper(networkHelper: NetworkHelperImpl): NetworkHelper

    @Binds
    @Singleton
    abstract fun bindNetworkChangeObserver(networkChangeObserver: NetworkChangeObserverImpl): NetworkChangeObserver

    @Binds
    @Singleton
    abstract fun bindNetworkStatusMonitor(networkStatusMonitor: NetworkStatusMonitorImpl): NetworkStatusMonitor
}
