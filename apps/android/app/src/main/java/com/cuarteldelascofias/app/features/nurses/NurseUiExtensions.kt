package com.cuarteldelascofias.app.features.nurses

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.cuarteldelascofias.app.R
import com.cuarteldelascofias.app.data.model.Nurse

@Composable
fun Nurse.activeStatusLabel(): String {
    return if (active) {
        stringResource(R.string.nurse_status_activa)
    } else {
        stringResource(R.string.nurse_status_inactiva)
    }
}
