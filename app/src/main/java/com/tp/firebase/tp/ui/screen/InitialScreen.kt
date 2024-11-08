package com.tp.firebase.tp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tp.firebase.tp.R
import com.tp.firebase.tp.ui.theme.ButtonColorsPrimary
import com.tp.firebase.tp.ui.theme.ColorUserMessage
import com.tp.firebase.tp.ui.theme.TextColorPrimary

@Composable
fun InitialScreen(
    navigateToLogin: () -> Unit = {},
    navigateToSignUp: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(White, ColorUserMessage),
                    startY = 0f,
                    endY = 2500f
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Bienvenido",
            style = MaterialTheme.typography.headlineLarge,
            color = TextColorPrimary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "a tú",
            style = MaterialTheme.typography.headlineLarge,
            color = TextColorPrimary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "CHATBOT",
            style = MaterialTheme.typography.headlineLarge,
            color = TextColorPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 46.sp
        )
        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navigateToSignUp() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ButtonColorsPrimary),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = "Registrarse",
                style = MaterialTheme.typography.headlineSmall,
                color = White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(
            modifier = Modifier.clickable {},
            image = R.drawable.ic_launcher_foreground,
            text = "Continuar con Google"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Ya tengo cuenta",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable(onClick = { navigateToLogin() })
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun CustomButton(modifier: Modifier, image: Int, text: String) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 32.dp)
            .border(2.dp, ButtonColorsPrimary, CircleShape),
        contentAlignment = Alignment.CenterStart
    ) {

        Image(
            painter = painterResource(id = image),
            contentDescription = "Google Icon",
            modifier = Modifier
                .padding(start = 8.dp)
                .size(56.dp)
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = TextColorPrimary,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InitialScreenPreview() {
    InitialScreen()
}