package com.practicum.playlistmaker.ui.player.view_model

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlistmaker.creator.Consts
import com.practicum.playlistmaker.data.contentprovider.ContentProvider
import com.practicum.playlistmaker.data.settings.SettingsRepository
import com.practicum.playlistmaker.domain.player.PlayerInteractor
import com.practicum.playlistmaker.domain.player.models.PlayerState
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerViewModel(
    private val playerInteractor: PlayerInteractor,
    private val contentProvider: ContentProvider,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    private val _playState = MutableLiveData<Boolean>()
    val playState: LiveData<Boolean> = _playState

    var useDarkMode: Boolean = settingsRepository.getThemeSettings().darkTheme

    private val _playDuration = MutableLiveData<String>()
    val playDuration: LiveData<String> = _playDuration

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            _playDuration.postValue(
                SimpleDateFormat("mm:ss", Locale.getDefault())
                    .format(playerInteractor.getCurrentPosition())
            )
            handler.postDelayed(this, Consts.PLAY_TRACK_UPDATE_DELAY)
        }
    }

    private fun startPlayer(url: String) {
        playerInteractor.startPlayer(url)
        handler.post(runnable)
        _playState.value = true
    }

    fun pausePlayer() {
        playerInteractor.pausePlayer()
        handler.removeCallbacks(runnable)
        _playState.value = false
    }

    override fun onCleared() {
        playerInteractor.releasePlayer()
        _playState.value = false
        super.onCleared()
    }

    fun playbackControl(url: String) {
        when (playerInteractor.getPlayerState()) {
            PlayerState.PLAYING -> {
                pausePlayer()
            }

            PlayerState.PAUSED, PlayerState.PREPARED -> {
                startPlayer(url)
            }

            PlayerState.DEFAULT -> {
                startPlayer(url)
                playerInteractor.setTrackCompletionListener {
                    _playState.value = false
                    _playDuration.value = contentProvider.getStringFromResources("default_duration")
                    handler.removeCallbacks(runnable)
                }
            }
        }
    }



}