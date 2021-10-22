package com.kkstream.playcraftdemo.util

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings

object InjectorUtils {

    @SuppressLint("HardwareIds")
    fun provideDeviceId(context: Context): String {
        // Use device id is not recommend.
        // See: https://developer.android.com/training/articles/user-data-ids#working_with_instance_ids_&_guids
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
}