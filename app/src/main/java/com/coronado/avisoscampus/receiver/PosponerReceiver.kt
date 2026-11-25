package com.coronado.avisoscampus.receiver

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import androidx.core.app.NotificationManagerCompat
import com.coronado.avisoscampus.model.Aviso
import com.coronado.avisoscampus.model.Origen
import com.coronado.avisoscampus.notificacion.Notificador
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PosponerReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_POSPONER = "com.coronado.avisoscampus.POSPONER_RECORDATORIO"
        const val ACTION_MOSTRAR_POSPUESTO = "com.coronado.avisoscampus.MOSTRAR_RECORDATORIO_POSPUESTO"
        const val EXTRA_NOTIFICATION_ID = "NOTIFICATION_ID"
        const val EXTRA_TITULO = "TITULO"
        const val EXTRA_MENSAJE = "MENSAJE"
    }

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return
        val notificationId = intent.getIntExtra(EXTRA_NOTIFICATION_ID, 0)
        val titulo = intent.getStringExtra(EXTRA_TITULO) ?: "Recordatorio"
        val mensaje = intent.getStringExtra(EXTRA_MENSAJE) ?: ""

        when (action) {
            ACTION_POSPONER -> {
                // 1. Cancelar la notificación actual inmediatamente
                NotificationManagerCompat.from(context).cancel(notificationId)

                // 2. Programar la alarma a los 10 segundos
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
                val mostrarIntent = Intent(context, PosponerReceiver::class.java).apply {
                    this.action = ACTION_MOSTRAR_POSPUESTO
                    putExtra(EXTRA_NOTIFICATION_ID, notificationId)
                    putExtra(EXTRA_TITULO, titulo)
                    putExtra(EXTRA_MENSAJE, mensaje)
                }

                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    notificationId,
                    mostrarIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
                )

                val triggerAtMillis = SystemClock.elapsedRealtime() + 10_000L

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if (alarmManager?.canScheduleExactAlarms() == true) {
                        alarmManager.setExactAndAllowWhileIdle(
                            AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            triggerAtMillis,
                            pendingIntent,
                        )
                    } else {
                        alarmManager?.setAndAllowWhileIdle(
                            AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            triggerAtMillis,
                            pendingIntent,
                        )
                    }
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager?.setExactAndAllowWhileIdle(
                        AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        triggerAtMillis,
                        pendingIntent,
                    )
                } else {
                    alarmManager?.setExact(
                        AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        triggerAtMillis,
                        pendingIntent,
                    )
                }
            }

            ACTION_MOSTRAR_POSPUESTO -> {
                // 3. Volver a enviar el recordatorio pospuesto al canal canal_urgentes
                val hora = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                val aviso = Aviso(
                    id = if (notificationId != 0) notificationId else 999,
                    titulo = titulo,
                    texto = mensaje,
                    origen = Origen.RECORDATORIO,
                    hora = hora,
                )
                Notificador.mostrarUrgente(context, aviso)
            }
        }
    }
}
