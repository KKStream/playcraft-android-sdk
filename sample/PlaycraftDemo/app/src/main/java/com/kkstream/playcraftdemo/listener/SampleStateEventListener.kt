package com.kkstream.playcraftdemo.listener

import android.util.Log
import com.kkstream.android.daas.internal.utils.KKSLog
import com.kkstream.android.paas.player.common.PaaSProvider
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

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: PaaSProvider.PlaybackState) {
        when (playbackState) {
            PaaSProvider.PlaybackState.READY -> {
                if (playWhenReady)
                    Log.d(TAG, "start to play")
            }
            PaaSProvider.PlaybackState.ENDED -> Log.d(TAG, "player ended")
        }
    }
}