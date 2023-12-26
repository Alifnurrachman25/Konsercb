package com.example.konsercb

import android.app.Application
import com.example.konsercb.repositori.ContainerApp
import com.example.konsercb.repositori.ContainerDataApp

class AplikasiKonser : Application() {
    /**
     * Appcontainer instance digunakan oleh kelas kelas lainnya untuk mendapatkan depedensi
     */

    lateinit var container: ContainerApp

    override fun onCreate(){
        super.onCreate()
        container = ContainerDataApp(this)
    }
}