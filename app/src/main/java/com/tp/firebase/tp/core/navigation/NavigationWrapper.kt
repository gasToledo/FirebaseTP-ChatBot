package com.tp.firebase.tp.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tp.firebase.tp.ui.ChatScreenViewModel
import com.tp.firebase.tp.ui.screen.ChatScreen
import com.tp.firebase.tp.ui.screen.InitialScreen
import com.tp.firebase.tp.ui.screen.LoginScreen
import com.tp.firebase.tp.ui.screen.SignUpScreen

@Composable
fun NavigationWrapper(
    modifier: Modifier,
    navController: NavHostController,
    auth: FirebaseAuth,
    db: FirebaseFirestore,
    chatScreenViewModel: ChatScreenViewModel
) {


    NavHost(
        navController = navController,
        startDestination = if (auth.currentUser != null) "chat" else ("initial")
    ) {
        composable("initial") {
            InitialScreen(
                navigateToLogin = { navController.navigate("login") },
                navigateToSignUp = { navController.navigate("signUp") }
            )
        }

        composable("login") {
            LoginScreen(
                auth = auth,
                navigateToChat = { navController.navigate("chat") },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable("signUp") {
            SignUpScreen(
                auth = auth,
                navigateToChat = { navController.navigate("chat") },
                navigateBack = { navController.popBackStack() })
        }

        composable("chat") {
            ChatScreen(
                viewModel = chatScreenViewModel,
                backToInitial = {
                    navController.navigate("initial")
                    auth.signOut()
                },
                db = db
            )
        }

    }


}