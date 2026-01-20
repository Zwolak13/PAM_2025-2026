package com.a160131_zwolak_dawid.components.Dashboard.MainScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.a160131_zwolak_dawid.R
import com.a160131_zwolak_dawid.components.Navigation.Routes

@Composable
fun BmiCard(
    height: Float,
    weight: Float,
    navController: NavController
) {
    val bmiRaw = if (height > 0f) weight / ((height / 100f) * (height / 100f)) else 0f
    val bmiDisplay = String.format("%.2f", bmiRaw)

    val bmiLabelRes = when {
        bmiRaw <= 0f -> R.string.bmi_unknown
        bmiRaw < 18.5f -> R.string.bmi_underweight
        bmiRaw < 25f -> R.string.bmi_normal
        bmiRaw < 30f -> R.string.bmi_overweight
        else -> R.string.bmi_obese
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "${stringResource(R.string.bmi_label)}: $bmiDisplay",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(bmiLabelRes),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Button(
                onClick = {
                    navController.navigate(
                        Routes.userBmi(
                            height = height.toString().replace(',', '.'),
                            weight = weight.toString().replace(',', '.')
                        )
                    )
                }
            ) {
                Text(stringResource(R.string.edit))
            }
        }
    }
}
