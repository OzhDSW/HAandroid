package io.altenems.companion.android

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.altenems.companion.android.common.util.MessagingToken
import io.altenems.companion.android.common.util.MessagingTokenProvider
import io.altenems.companion.android.util.PlayServicesAvailability
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MinimalApplicationModule {
    @Provides
    @Singleton
    fun provideMessagingTokenProvider(): MessagingTokenProvider {
        return MessagingTokenProvider {
            return@MessagingTokenProvider MessagingToken("")
        }
    }

    @Provides
    @Singleton
    internal fun providesPlayServicesAvailability(): PlayServicesAvailability {
        return PlayServicesAvailability { false }
    }
}
