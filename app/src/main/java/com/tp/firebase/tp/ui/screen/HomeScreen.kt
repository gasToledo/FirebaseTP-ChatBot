package com.tp.firebase.tp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.crashlytics.FirebaseCrashlytics

@Composable
fun HomeScreen(modifier: Modifier) {

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Home Screen", modifier = modifier)

        Spacer(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
        )
        Button(
            onClick = {
                FirebaseCrashlytics.getInstance().log("Test Error Log")
                throw RuntimeException("Test Crash")
            },
            modifier = modifier
                .height(100.dp)
                .width(250.dp),

            ) {
            Text(text = "Error")
        }

    }
}
