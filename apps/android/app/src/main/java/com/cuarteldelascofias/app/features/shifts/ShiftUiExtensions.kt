package com.cuarteldelascofias.app.features.shifts

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.cuarteldelascofias.app.R
import com.cuarteldelascofias.app.data.model.ShiftStatus

@Composable
fun ShiftStatus.label(): String {
    return when (this) {
        ShiftStatus.PENDING -> stringResource(R.string.shift_status_pendiente)
        ShiftStatus.SCHEDULED -> stringResource(R.string.shift_status_programada)
        ShiftStatus.IN_PROGRESS -> stringResource(R.string.shift_status_en_curso)
        ShiftStatus.COMPLETED -> stringResource(R.string.shift_status_terminada)
        ShiftStatus.CANCELLED -> stringResource(R.string.shift_status_cancelada)
    }
}
