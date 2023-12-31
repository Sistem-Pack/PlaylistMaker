package com.practicum.playlistmaker.domain.search.impl

import com.practicum.playlistmaker.domain.search.api.TrackSearchInteractor
import com.practicum.playlistmaker.domain.search.api.TrackSearchRepository
import com.practicum.playlistmaker.domain.search.models.Track
import java.util.concurrent.ExecutorService

class TrackSearchInteractorImpl(private val repository: TrackSearchRepository, private val executor: ExecutorService) : TrackSearchInteractor {

    override fun search(expression: String, consumer: TrackSearchInteractor.TracksConsumer) {
        executor.execute {
            val tracks = repository.search(expression)
            when (repository.errorCode) {
                200 -> consumer.consume(tracks, null)
                else -> consumer.consume(null, repository.errorCode.toString())
            }
        }
    }

    override fun readSearchHistory(): ArrayList<Track> {
        return repository.readSearchHistory()
    }

    override fun saveHistory(tracks: List<Track>) {
        repository.saveHistory(tracks)
    }

    override fun clearSearchHistory() {
        repository.clearSearchHistory()
    }
}