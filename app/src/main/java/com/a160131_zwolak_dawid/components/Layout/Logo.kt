package com.a160131_zwolak_dawid.components.Layout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a160131_zwolak_dawid.R

@Composable
fun Logo() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = stringResource(R.string.logo_first_name),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 42.sp,
                color = MaterialTheme.colorScheme.primary,
            ),
            modifier = Modifier
                .offset(x = -40.dp)

        )

        Text(
            text = stringResource(R.string.logo_last_name),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 28.sp,

            ),
            modifier = Modifier
                .offset(y = 28.dp, x = 30.dp)
        )
    }


    Text(
        text = stringResource(R.string.logo_index),
        style = MaterialTheme.typography.bodyMedium.copy(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .offset(x = -30.dp),
        textAlign = TextAlign.Center
    )
}
