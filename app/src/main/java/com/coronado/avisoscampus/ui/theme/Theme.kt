package com.coronado.avisoscampus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val EsquemaClaro = lightColorScheme(
    primary = Azul,
    onPrimary = Color.White,
    primaryContainer = AzulClaro,
    error = Rojo,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002)
)

@Composable
fun AvisosCampusTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaClaro,
        content = content
    )
}