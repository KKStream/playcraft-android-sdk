package com.kkstream.playcraftdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import com.kkstream.android.paas.player.common.PaaSConfig
import com.kkstream.android.paas.player.common.PaaSController
import com.kkstream.android.paas.player.common.PaaSProvider
import com.kkstream.android.paas.player.common.callback.DialogEventListener
import com.kkstream.android.paas.player.common.event.error.PaaSErrorEvent
import com.kkstream.android.paas.player.mobile.DefaultDialogEventListener
import com.kkstream.android.paas.player.mobile.DefaultMenuFactory
import com.kkstream.playcraftdemo.databinding.ActivityPlaybackBinding
import com.kkstream.playcraftdemo.viewmodel.PlaybackViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaybackActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlaybackBinding
    private val viewModel: PlaybackViewModel by viewModels()

    private val paaSController: PaaSController?
        get() = paasProvider?.getController()
    private var paasProvider: PaaSProvider? = null
    private val dialogEventListener: DialogEventListener
        get() = DefaultDialogEventListener(this, binding.kksPlayerServiceView)
    private val menuFactory: DefaultMenuFactory
        get() = DefaultMenuFactory(binding.kksPlayerServiceView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaybackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set config
        PaaSConfig.setShowAutoPlay(this, false)

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

        binding.kksPlayerServiceView.setupMenuItems(getMenuItem())
    }

    /**
     * add customize and player's default view in the right-top corner of PaaSView
     */
    private fun getMenuItem(): List<View> {
        return listOf(
            menuFactory.createCastMenuItem(),
            menuFactory.createSettingMenuItem(),
            createSampleMenu()
        )
    }

    /**
     * Customize view
     */
    private fun createSampleMenu(): View {
        val inflater = LayoutInflater.from(this)
        val menu =
            inflater.inflate(R.layout.widget_playback_menu_sample, binding.kksPlayerServiceView, false)
        menu.setOnClickListener {
            paaSController?.pause()

            // TODO do something
        }

        return menu
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