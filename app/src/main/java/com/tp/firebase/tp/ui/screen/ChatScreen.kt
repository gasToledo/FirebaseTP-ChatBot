package com.tp.firebase.tp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tp.firebase.tp.domain.MessageModel
import com.tp.firebase.tp.ui.ChatScreenViewModel
import com.tp.firebase.tp.ui.theme.ButtonColorsPrimary
import com.tp.firebase.tp.ui.theme.ColorModelMessage
import com.tp.firebase.tp.ui.theme.ColorUserMessage
import com.tp.firebase.tp.ui.theme.TextColorPrimary

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatScreenViewModel,
    db: FirebaseFirestore,
    backToInitial: () -> Unit = {},
) {




    Column(modifier = modifier) {

        val messageList by viewModel.messageList.collectAsState()

        AppHeader(backToInitial)

        MessageList(modifier = Modifier.weight(1f).padding(horizontal = 8.dp), messages = messageList)

        Spacer(modifier = Modifier.height(8.dp))

        MessageInput(
            onMessageSend = {
                viewModel.sendMessage(it)
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

    }
}

@Composable
private fun MessageList(modifier: Modifier = Modifier, messages: List<MessageModel>) {

    if (messages.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "¡Escribe algo!",
                fontWeight = FontWeight.W500,
                color = Color.Black,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                textAlign = TextAlign.Center
            )
        }
    }
    LazyColumn(
        modifier = modifier,
        reverseLayout = true
    ) {
        items(messages.reversed()) {
            MessageRow(it)
        }
    }
}

@Composable
private fun MessageRow(message: MessageModel) {
    val isModel = message.rol == "model"

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {

            Box(
                modifier = Modifier
                    .align(
                        if (isModel) Alignment.BottomStart else Alignment.BottomEnd
                    )
                    .padding(
                        start = if (isModel) 8.dp else 70.dp,
                        end = if (isModel) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(
                        RoundedCornerShape(size = 16.dp)
                    )
                    .background(
                        if (isModel) ColorModelMessage else ColorUserMessage
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = message.message.toString(),
                    fontWeight = FontWeight.W500,
                    color = Color.White
                )
            }
        }
    }
}


@Composable
private fun MessageInput(onMessageSend: (String) -> Unit) {

    var currentMessage by remember { mutableStateOf(" ") }

    Row(
        modifier = Modifier.padding(start = 8.dp, bottom = 16.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = currentMessage,
            onValueChange = { currentMessage = it },
            placeholder = { Text(text = "Type a message") },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = ButtonColorsPrimary,
                focusedBorderColor = ButtonColorsPrimary,
            ),
            shape = RoundedCornerShape(16.dp),
            singleLine = true
        )

        IconButton(
            onClick = {
                if (currentMessage.trim().isNotEmpty()) {
                    onMessageSend(currentMessage.trim())
                    currentMessage = ""
                }
            },
            enabled = currentMessage.trim().isNotEmpty()
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send",
                tint = TextColorPrimary
            )
        }
    }
}

@Composable
private fun AppHeader(backToInitial: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(ButtonColorsPrimary),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        backToInitial()
                    },
                imageVector = Icons.AutoMirrored.Filled.Logout,
                contentDescription = "Cerrar sesión",
                tint = Color.White,
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                modifier = Modifier.padding(16.dp),
                text = "Chat bot",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

    }
}