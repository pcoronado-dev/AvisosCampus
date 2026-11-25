package com.coronado.avisoscampus

import android.app.Application
import com.coronado.avisoscampus.notificacion.Notificador

class AvisosApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Notificador.crearCanales(this)
    }
}
