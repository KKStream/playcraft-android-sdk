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
                hostUrl = "https://${HOST_NAME}/v1",
                contentId = "${MEDIA_ID}",
                contentType = ContentType.Videos,
                accessToken = "${TOKEN}",
                deviceId = InjectorUtils.provideDeviceId(context),
                stateEventListener = SampleStateEventListener(),
        )
    }
}