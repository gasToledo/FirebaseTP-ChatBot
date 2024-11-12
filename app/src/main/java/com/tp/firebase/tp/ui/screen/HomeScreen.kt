package com.tp.firebase.tp.ui.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tp.firebase.tp.ui.HomeViewModel
import com.tp.firebase.tp.ui.theme.ButtonColorsPrimary


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    onNavigateToChat: () -> Unit = {},
    signOut: () -> Unit = {},
    crashTest: () -> Unit = {},
    isChatActive: Boolean = false
) {

    viewModel.getUsername()


    viewModel.logEvent()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier
            .weight(1f)
            .fillMaxWidth())

        Text(
            text = "Bienvenido ${viewModel.username.collectAsState().value}",
            modifier = Modifier,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.W500
        )
        Spacer(modifier = Modifier
            .weight(1f)
            .fillMaxWidth())

        Button(
            onClick = {
                crashTest()
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
                if (isChatActive) onNavigateToChat() else Log.d(
                    "HomeScreen",
                    "HomeScreen: Chat deshabilitado"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 16.dp),
            colors = if (isChatActive) ButtonDefaults.buttonColors(
                containerColor = ButtonColorsPrimary
            ) else ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer)
        ) {
            if (isChatActive) {
                Text(text = "Ir al chat", style = MaterialTheme.typography.titleMedium)
            } else {
                Text(text = "Deshabilitado", style = MaterialTheme.typography.titleMedium)
            }
        }

        Spacer(modifier = Modifier
            .height(12.dp)
            .fillMaxWidth())

        Text(
            modifier = Modifier.clickable { signOut() },
            text = "Cerrar sesion",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier
            .weight(1f)
            .fillMaxWidth())

    }
}

