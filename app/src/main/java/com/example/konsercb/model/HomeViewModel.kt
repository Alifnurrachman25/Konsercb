package com.example.konsercb.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.konsercb.database.Event
import com.example.konsercb.repositori.RepositoriEvent
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(private val repositoriSiswa: RepositoriEvent) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val homeUiState: StateFlow<HomeUiState> = repositoriSiswa.getAllEventStream().filterNotNull()
        .map { HomeUiState(listEvent = it.toList()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState()
        )


    data class HomeUiState(
        val listEvent: List<Event> = listOf()
    )
}