package com.cuarteldelascofias.app.features.patients

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.cuarteldelascofias.app.R
import com.cuarteldelascofias.app.data.model.Patient

@Composable
fun Patient.activeStatusLabel(): String {
    return if (active) {
        stringResource(R.string.patient_status_activo)
    } else {
        stringResource(R.string.patient_status_inactivo)
    }
}

@Composable
fun Patient.shiftSummary(): String {
    return if (activeShiftCount > 0) {
        stringResource(R.string.patient_guardias_activas, activeShiftCount)
    } else {
        stringResource(R.string.patient_guardias_sin_activas)
    }
}
