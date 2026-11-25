package com.coronado.avisoscampus.notificacion

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.coronado.avisoscampus.MainActivity
import com.coronado.avisoscampus.R
import com.coronado.avisoscampus.model.Aviso
import com.coronado.avisoscampus.model.Origen

object Notificador {
    const val CANAL_URGENTES = "canal_urgentes"
    const val CANAL_AVISOS = "canal_avisos"

    fun crearCanales(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(NotificationManager::class.java) ?: return

            val canalUrgentes = NotificationChannel(
                CANAL_URGENTES,
                "Urgentes",
                NotificationManager.IMPORTANCE_HIGH,
            ).apply {
                description = "Notificaciones importantes, como recordatorios."
            }

            val canalAvisos = NotificationChannel(
                CANAL_AVISOS,
                "Avisos",
                NotificationManager.IMPORTANCE_DEFAULT,
            ).apply {
                description = "Notificaciones generales de la aplicación."
            }

            manager.createNotificationChannel(canalUrgentes)
            manager.createNotificationChannel(canalAvisos)
        }
    }

    fun crearCanal(context: Context) {
        crearCanales(context)
    }

    fun tienePermiso(context: Context): Boolean {
        return (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) ||
                (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED)
    }

    @SuppressLint("MissingPermission")
    fun mostrarUrgente(context: Context, aviso: Aviso): Boolean {
        return mostrarNotificacion(
            context = context,
            aviso = aviso,
            canalId = CANAL_URGENTES,
            prioridad = NotificationCompat.PRIORITY_HIGH,
        )
    }

    @SuppressLint("MissingPermission")
    fun mostrarGeneral(context: Context, aviso: Aviso): Boolean {
        return mostrarNotificacion(
            context = context,
            aviso = aviso,
            canalId = CANAL_AVISOS,
            prioridad = NotificationCompat.PRIORITY_DEFAULT,
        )
    }

    @SuppressLint("MissingPermission")
    fun mostrar(context: Context, aviso: Aviso): Boolean {
        return when (aviso.origen) {
            Origen.RECORDATORIO -> mostrarUrgente(context, aviso)
            else -> mostrarGeneral(context, aviso)
        }
    }

    @SuppressLint("MissingPermission")
    private fun mostrarNotificacion(
        context: Context,
        aviso: Aviso,
        canalId: String,
        prioridad: Int,
    ): Boolean {
        if (!tienePermiso(context)) return false

        val intent = Intent(context, MainActivity::class.java)
        val pendiente = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val notificacion = NotificationCompat.Builder(context, canalId)
            .setSmallIcon(R.drawable.ic_notificacion)
            .setContentTitle(aviso.titulo)
            .setContentText(aviso.texto)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(aviso.texto)
            )
            .setPriority(prioridad)
            .setContentIntent(pendiente)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(aviso.id, notificacion)
        return true
    }
}
