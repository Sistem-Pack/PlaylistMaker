package com.practicum.playlistmaker.ui.search.view_model

import com.practicum.playlistmaker.domain.search.models.Track

sealed interface SearchState {
    data object Loading : SearchState
    data class Content(val tracks: List<Track>) : SearchState
    data object AllGone : SearchState
    data class Error(val errorMessage: String) : SearchState
    data class SearchHistory(val tracks: List<Track>) : SearchState
    data object Empty : SearchState
}
