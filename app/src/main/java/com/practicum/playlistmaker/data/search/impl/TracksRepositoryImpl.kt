package com.practicum.playlistmaker.data.search.impl

import com.practicum.playlistmaker.data.network.NetworkClient
import com.practicum.playlistmaker.data.search.dto.TrackSearchRequest
import com.practicum.playlistmaker.data.search.dto.TrackSearchResponse
import com.practicum.playlistmaker.domain.search.api.TrackSearchRepository
import com.practicum.playlistmaker.domain.search.models.Track

class TracksRepositoryImpl(private val networkClient: NetworkClient, private val tracksHistoryStorage: TracksHistoryStorage) : TrackSearchRepository {
    override var errorCode: Int = 0

    override fun search(expression: String): ArrayList<Track> {
        val response = networkClient.doRequest(TrackSearchRequest(expression))
        errorCode = response.resultCode
        if (errorCode == 200) {
            return (response as TrackSearchResponse).results.map {
                Track(
                    it.trackId,
                    it.trackName,
                    it.artistName,
                    it.trackTimeMillis,
                    it.artworkUrl100,
                    it.collectionName,
                    it.releaseDate,
                    it.primaryGenreName,
                    it.country,
                    it.previewUrl
                )
            } as ArrayList<Track>
        } else {
            return arrayListOf()
        }
    }

    override fun readSearchHistory(): ArrayList<Track> {
        TODO("Not yet implemented")
    }

    override fun saveHistory(tracks: List<Track>) {
        TODO("Not yet implemented")
    }

    override fun clearSearchHistory() {
        TODO("Not yet implemented")
    }
}