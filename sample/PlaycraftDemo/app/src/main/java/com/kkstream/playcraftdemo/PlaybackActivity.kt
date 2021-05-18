package com.kkstream.playcraftdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.kkstream.android.paas.player.common.PaaSProvider
import com.kkstream.android.paas.player.common.callback.DialogEventListener
import com.kkstream.android.paas.player.common.event.error.PaaSErrorEvent
import com.kkstream.android.paas.player.mobile.DefaultDialogEventListener
import com.kkstream.playcraftdemo.databinding.ActivityPlaybackBinding
import com.kkstream.playcraftdemo.viewmodel.PlaybackViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaybackActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlaybackBinding
    private val viewModel: PlaybackViewModel by viewModels()

    private var paasProvider: PaaSProvider? = null
    private val dialogEventListener: DialogEventListener
        get() = DefaultDialogEventListener(this, binding.kksPlayerServiceView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaybackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            paasProvider = binding.kksPlayerServiceView.setup(
                    paasParameter = viewModel.kksPlayerServiceParamData,
                    lifecycle = lifecycle
            )
        } catch (e: PaaSErrorEvent) {
            Log.e(TAG, e.getErrorMessage(this))
        } catch (e: IllegalStateException) {
            // fragment not attach to context
            Log.e(TAG, e.toString())
        }

        setupDialogListener()
    }

    /**
     * Set the [DialogEventListener] to [com.kkstream.android.paas.player.mobile.PaaSView]
     */
    private fun setupDialogListener() {
        //Passing the customized DialogEventListener if would like to show customized dialog.
        binding.kksPlayerServiceView.setDialogEventListener(dialogEventListener)
    }

    companion object {
        private val TAG = PlaybackActivity::class.java.simpleName
    }
}