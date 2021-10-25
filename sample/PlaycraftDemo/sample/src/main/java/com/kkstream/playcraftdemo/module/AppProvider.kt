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
        // Please contact us directly if you want test mock data
        return PaaSParameter(
            hostUrl = "${HOST_NAME}",
            contentId = "${MEDIA_ID}",
            contentType = ContentType.Videos,
            accessToken = "${access_tokn}",
            deviceId = InjectorUtils.provideDeviceId(context),
            stateEventListener = SampleStateEventListener(),
            // depends on your backend team's requirement
            customHeader = mapOf(
                "${HEADER_NAME_1}" to "${HEADER_VALUE_1}",
                "${HEADER_NAME_2}" to "${HEADER_VALUE_2}",
            )
        )
    }
}