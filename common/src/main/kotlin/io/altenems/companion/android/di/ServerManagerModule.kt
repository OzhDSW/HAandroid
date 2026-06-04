package io.altenems.companion.android.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.altenems.companion.android.common.data.servers.ServerManager
import io.altenems.companion.android.common.data.servers.ServerManagerImpl
import javax.inject.Singleton

/**
 * Module providing the [ServerManager] binding.
 *
 * Extracted to a separate module to allow tests to uninstall it and provide a mock.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class ServerManagerModule {

    @Binds
    @Singleton
    internal abstract fun bindServerManager(serverManager: ServerManagerImpl): ServerManager
}
