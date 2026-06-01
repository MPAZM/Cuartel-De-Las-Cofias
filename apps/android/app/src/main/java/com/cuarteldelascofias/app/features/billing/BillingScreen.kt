package com.cuarteldelascofias.app.features.billing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cuarteldelascofias.app.R
import com.cuarteldelascofias.app.core.common.AppContainer
import com.cuarteldelascofias.app.core.designsystem.AppDetailRow
import com.cuarteldelascofias.app.core.designsystem.AppInfoCard
import com.cuarteldelascofias.app.core.designsystem.AppMetricCard
import com.cuarteldelascofias.app.core.designsystem.AppSectionTitle

@Composable
fun BillingScreen(
    modifier: Modifier = Modifier,
    viewModel: BillingViewModel = viewModel(
        factory = BillingViewModel.factory(
            shiftRepository = AppContainer.shiftRepository,
            patientRepository = AppContainer.patientRepository
        )
    )
) {
    val uiState = viewModel.uiState

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.billing_intro),
            style = MaterialTheme.typography.bodyLarge
        )

        AppSectionTitle(text = stringResource(R.string.billing_summary_title))

        AppMetricCard(
            label = stringResource(R.string.billing_total_collected),
            value = uiState.totalCollected
        )
        AppMetricCard(
            label = stringResource(R.string.billing_total_commissions),
            value = uiState.totalCommissions
        )
        AppMetricCard(
            label = stringResource(R.string.billing_total_nurse_net),
            value = uiState.totalNurseNet
        )

        AppSectionTitle(text = stringResource(R.string.billing_recent_title))

        if (uiState.items.isEmpty()) {
            Text(
                text = stringResource(R.string.billing_empty),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        uiState.items.forEach { item ->
            AppInfoCard(
                title = item.patientName,
                body = item.shiftLabel
            )
            AppDetailRow(
                label = stringResource(R.string.shift_label_tarifa),
                value = item.rateAmount
            )
            AppDetailRow(
                label = stringResource(R.string.shift_label_comision),
                value = item.commissionAmount
            )
            AppDetailRow(
                label = stringResource(R.string.shift_label_pago_neto),
                value = item.nurseNetAmount
            )
            HorizontalDivider()
        }
    }
}
