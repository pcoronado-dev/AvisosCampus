package com.coronado.avisoscampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.coronado.avisoscampus.data.AvisosRepositoryMemoria
import com.coronado.avisoscampus.ui.PantallaAvisos
import com.coronado.avisoscampus.ui.theme.AvisosCampusTheme
import com.coronado.avisoscampus.viewmodel.AvisosViewModel
import com.coronado.avisoscampus.viewmodel.AvisosViewModelFactory

class MainActivity : ComponentActivity() {

    private val vm: AvisosViewModel by viewModels {
        AvisosViewModelFactory(AvisosRepositoryMemoria())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AvisosCampusTheme {
                PantallaAvisos(vm)
            }
        }
    }
}