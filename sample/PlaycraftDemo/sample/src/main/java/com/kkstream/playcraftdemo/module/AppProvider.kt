package com.kkstream.playcraftdemo.module

import android.content.Context
import com.kkstream.android.paas.player.common.ContentType
import com.kkstream.android.paas.player.common.PaaSParameter
import com.kkstream.playcraftdemo.listener.SampleStateEventListener
import com.kkstream.playcraftdemo.util.InjectorUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppProvider {

    @Provides
    @Singleton
    fun providePaasParam(
        @ApplicationContext context: Context
    ): PaaSParameter {
        return PaaSParameter(
            hostUrl = "https://mock-playback.tfc.kkv-test.com",
            contentId = "1",
            contentType = ContentType.Videos,
            accessToken = "paas5@gmail.com",
            deviceId = InjectorUtils.provideDeviceId(context),
            stateEventListener = SampleStateEventListener(),
            customHeader = mapOf(
                "X-Device-Type" to "android",
                "PaaS-Sample-Platform-Type" to "android-streaming"
            )
        )
    }
}