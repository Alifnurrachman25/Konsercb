package com.example.konsercb.model


import android.text.Spannable.Factory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.konsercb.AplikasiKonser

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(aplikasiSiswa().container.repositoriEvent)
        }
        initializer {
            EntryEventViewModel(aplikasiSiswa().container.repositoriEvent)
        }
        initializer {
            DetailEventViewModel(createSavedStateHandle(), aplikasiSiswa().container.repositoriEvent)
        }
        initializer {
            EditEventViewModel(createSavedStateHandle(), aplikasiSiswa().container.repositoriEvent)
        }
    }
}

/**
 * Extension function for creating [AplikasiKonser] instance from [CreationExtras].
 */
fun CreationExtras.aplikasiSiswa(): AplikasiKonser =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AplikasiKonser)
