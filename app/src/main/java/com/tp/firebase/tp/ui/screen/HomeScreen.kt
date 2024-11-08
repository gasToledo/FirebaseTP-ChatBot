package com.tp.firebase.tp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.tp.firebase.tp.R
import com.tp.firebase.tp.ui.theme.ButtonColorsPrimary

@Composable
fun HomeScreen(modifier: Modifier, username: String, onNavigateBack: () -> Unit = {}) {

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            painter = painterResource(R.drawable.baseline_arrow_back_24),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 16.dp,end = 320.dp)
                .size(32.dp)
                .clickable { onNavigateBack() },

        )

        Spacer(modifier = Modifier
            .weight(1f)
            .fillMaxWidth())

        Text(
            text = "Bienvenido $username",
            modifier = Modifier,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.W500
        )

        Spacer(modifier = Modifier
            .weight(1f)
            .fillMaxWidth())

        Button(
            onClick = {
                FirebaseCrashlytics.getInstance().log("Test Error Log")
                throw RuntimeException("Test Crash")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonColorsPrimary
            )
            ) {
            Text(text = "Lanzar error", style = MaterialTheme.typography.titleMedium)
        }

        Spacer(modifier = Modifier
            .height(32.dp)
            .fillMaxWidth())

        Button(
            onClick = {

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonColorsPrimary
            )
        ) {
            Text(text = "Ir al chat", style = MaterialTheme.typography.titleMedium)
        }

        Spacer(modifier = Modifier
            .height(12.dp)
            .fillMaxWidth())

        Text(text = "Cerrar sesion", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier
            .weight(1f)
            .fillMaxWidth())

    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(modifier = Modifier, "Gas")
}
