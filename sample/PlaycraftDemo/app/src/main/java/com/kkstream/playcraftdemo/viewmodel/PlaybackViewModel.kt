package com.kkstream.playcraftdemo.viewmodel

import androidx.lifecycle.ViewModel
import com.kkstream.android.paas.player.common.PaaSParameter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaybackViewModel @Inject constructor(
    val kksPlayerServiceParamData: PaaSParameter
) : ViewModel() {

}