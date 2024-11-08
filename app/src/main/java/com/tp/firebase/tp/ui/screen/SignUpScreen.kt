package com.tp.firebase.tp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.tp.firebase.tp.R
import com.tp.firebase.tp.ui.theme.ButtonColorsPrimary
import com.tp.firebase.tp.ui.theme.ColorUserMessage

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    auth: FirebaseAuth,
    navigateToChat: () -> Unit = {},
    navigateBack: () -> Unit = {}
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var authError by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush
                    .verticalGradient(
                        listOf(White, ColorUserMessage),
                        startY = 0f,
                        endY = 2500f
                    )
            )
            .padding(horizontal = 24.dp, vertical = 12.dp),
    ) {
        Icon(
            painter = painterResource(R.drawable.baseline_arrow_back_24),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clickable { navigateBack() }
        )

        Spacer(Modifier.weight(0.2f))

        if (authError) {
            Snackbar(
                action = {
                    TextButton(onClick = { authError = false }) {
                        Text("Dismiss")
                    }
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = errorMessage)
            }
        }

        Text(
            text = "Email",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            modifier = Modifier.padding(start = 22.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Enter your email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = ButtonColorsPrimary,
                unfocusedContainerColor = Color.LightGray,
                focusedBorderColor = ButtonColorsPrimary,
                focusedContainerColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.baseline_email_24),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        )


        Spacer(Modifier.height(32.dp))

        Text(
            text = "Contraseña",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            modifier = Modifier.padding(start = 22.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Enter your password") },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = ButtonColorsPrimary,
                unfocusedContainerColor = Color.LightGray,
                focusedBorderColor = ButtonColorsPrimary,
                focusedContainerColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.baseline_lock_24),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
        )

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {
                if (email.isNotBlank() || password.isNotBlank()) {

                    isLoading = true

                    auth.createUserWithEmailAndPassword(
                        email,
                        password
                    ).addOnCompleteListener {
                        isLoading = false
                        if (it.isSuccessful) {
                            navigateToChat()
                        } else {
                            println("Error al registrarse ${it.exception}")
                        }
                    }
                } else {
                    errorMessage = "Por favor, ingrese un correo electrónico y una contraseña."
                    authError = true
                }
            },
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 42.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ButtonColorsPrimary),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(
                    text = "Registrarse",
                    style = MaterialTheme.typography.headlineSmall,
                    color = White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(Modifier.weight(1f))
    }
}