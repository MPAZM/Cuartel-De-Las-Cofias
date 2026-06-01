package com.cuarteldelascofias.app.features.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cuarteldelascofias.app.R
import com.cuarteldelascofias.app.core.designsystem.AppInfoCard
import com.cuarteldelascofias.app.core.designsystem.AppMetricCard
import com.cuarteldelascofias.app.core.designsystem.AppSectionTitle

@Composable
fun DashboardScreen(
    onGuardiasClick: () -> Unit,
    onPacientesClick: () -> Unit,
    onEnfermerasClick: () -> Unit,
    onCobrosClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.dashboard_bienvenida),
            style = MaterialTheme.typography.bodyLarge
        )

        AppSectionTitle(text = stringResource(R.string.dashboard_resumen))

        AppMetricCard(
            label = stringResource(R.string.label_guardias_activas),
            value = "3"
        )
        AppMetricCard(
            label = stringResource(R.string.label_pacientes_activos),
            value = "4"
        )
        AppMetricCard(
            label = stringResource(R.string.label_enfermeras_disponibles),
            value = "6"
        )
        AppMetricCard(
            label = stringResource(R.string.label_cobros_pendientes),
            value = "2"
        )

        AppSectionTitle(text = stringResource(R.string.dashboard_accesos))

        AppInfoCard(
            title = stringResource(R.string.nav_guardias),
            body = "Consulta las guardias programadas, activas y listas para cierre.",
            actionLabel = stringResource(R.string.accion_ir_a_guardias),
            onActionClick = onGuardiasClick
        )

        AppInfoCard(
            title = stringResource(R.string.nav_pacientes),
            body = "Mantén a la mano los datos mínimos de cada caso y su historial de guardias.",
            actionLabel = stringResource(R.string.accion_ir_a_pacientes),
            onActionClick = onPacientesClick
        )

        AppInfoCard(
            title = stringResource(R.string.nav_enfermeras),
            body = "Revisa disponibilidad, perfil operativo y notas de confianza por enfermera.",
            actionLabel = stringResource(R.string.accion_ir_a_enfermeras),
            onActionClick = onEnfermerasClick
        )

        AppInfoCard(
            title = stringResource(R.string.nav_cobros),
            body = "Visualiza tarifas, comisiones y montos netos para mantener control financiero.",
            actionLabel = stringResource(R.string.accion_ir_a_cobros),
            onActionClick = onCobrosClick
        )
    }
}
