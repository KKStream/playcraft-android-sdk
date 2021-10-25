package com.example.tvsample

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.FragmentActivity
import com.kkstream.android.paas.player.common.ContentType
import com.kkstream.android.paas.player.common.PaaSParameter
import com.kkstream.android.paas.player.common.PaaSProvider
import com.kkstream.android.paas.player.common.callback.ErrorEventCallback
import com.kkstream.android.paas.player.common.event.error.PaaSErrorEvent
import com.kkstream.android.paas.player.stb.CustomPaaSTVFragmentInterface
import com.kkstream.android.paas.player.stb.PaasTVFragment

class PlaybackActivity : FragmentActivity() {

    companion object {
        private val TAG = PlaybackActivity::class.java.simpleName
    }

    private lateinit var paasProvider: PaaSProvider

    private lateinit var paasTVFragment: PaasTVFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initial()
    }

    private fun initial() {
        // Reference PaasTVFragment to variable.
        paasTVFragment =
            supportFragmentManager.findFragmentById(R.id.paasTVFragment) as PaasTVFragment

        // Setup parameter to start play video.
        try {
            paasProvider = paasTVFragment.setup(
                paasParam = getPaaSParameter(),
                lifecycle = lifecycle
            )
        } catch (e: PaaSErrorEvent) {
            Log.e(TAG, e.getErrorMessage(this))
        } catch (e: IllegalStateException) {
            // fragment not attach to context
            Log.e(TAG, e.toString())
        }

        // The function of setPaasThemeColorStyle() only work after setup().
        paasTVFragment.setPaasThemeColorStyle(R.style.UpdateCustomizeIconColor)
    }

    private fun getPaaSParameter() = PaaSParameter(
        hostUrl = "${HOST_NAME}",
        contentId = "${MEDIA_ID}",
        contentType = ContentType.Videos,
        accessToken = "${access_tokn}",
        deviceId = Build.MODEL,
        customHeader = mapOf(
            "${HEADER_NAME_1}" to "${HEADER_VALUE_1}",
            "${HEADER_NAME_2}" to "${HEADER_VALUE_2}",
        ),
        errorEventCallback = getErrorEventCallback(),
        /** Optional, if value is null handle by internal. */
        /** Optional, if value is null handle by internal. */
        autoKeepScreenOnEnabled = true,
        /** Optional. */
        /** Optional. */
        thumbnailSeekingEnabled = true
        /** Optional. */
        /** Optional. */
    )

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return if (isFragmentHandleOnKeyUpEvent(keyCode, event)) {
            // If isFragmentHandleOnKeyUpEvent return true, it means we handle the onKeyUp event.
            // So we return true to tell parent we has handled it.
            true
        } else {
            super.onKeyUp(keyCode, event)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (isFragmentHandleOnKeyDownEvent(keyCode, event)) {
            // If isFragmentHandleOnKeyUpEvent return true, it means we handle the onKeyUp event.
            // So we return true to tell parent we has handled it.
            true
        } else {
            super.onKeyDown(keyCode, event)
        }
    }

    /**
     * For bypassing key event to paasTVFragment.
     */
    private fun isFragmentHandleOnKeyUpEvent(keyCode: Int, event: KeyEvent?): Boolean {
        return (paasTVFragment as? CustomPaaSTVFragmentInterface)?.onKeyUp(keyCode, event, null)
            ?: false
    }

    /**
     * For bypassing key event to paasTVFragment.
     */
    private fun isFragmentHandleOnKeyDownEvent(keyCode: Int, event: KeyEvent?): Boolean {
        return (paasTVFragment as? CustomPaaSTVFragmentInterface)?.onKeyDown(keyCode, event, null)
            ?: false
    }

    /**
     * Handle error event if needed.
     */
    private fun getErrorEventCallback() = object : ErrorEventCallback {
        override fun onPaaSError(errorEvent: PaaSErrorEvent): Boolean {

//            when (errorEvent) {
//                is PaaSErrorEvent.BasicNetworkError -> {
//                    if (errorEvent.type == BasicNetworkErrorType.NETWORK_ERROR) {
//                        // Return true to intercept event, and show dialog by self.
//                        // showErrorDialog(errorEvent.message)
//                        return true
//                    }
//                }
//            }

            // Return false let library to show error dialog.
            return false
        }
    }
}