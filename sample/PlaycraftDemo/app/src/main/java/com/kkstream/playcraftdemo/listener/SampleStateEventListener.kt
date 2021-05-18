package com.kkstream.playcraftdemo.listener

import com.kkstream.android.daas.internal.utils.KKSLog
import com.kkstream.android.paas.player.common.data.Content
import com.kkstream.android.paas.player.common.event.error.PaaSErrorEvent
import com.kkstream.android.paas.player.common.callback.StateEventListener

class SampleStateEventListener : StateEventListener {

    companion object {
        private val TAG = SampleStateEventListener::class.java.simpleName
    }

    override suspend fun onContentChanged(content: Content): PaaSErrorEvent? {
        KKSLog.i(TAG, "onContentChanged() new content: $content")
        return null
    }
}