package com.tp.firebase.tp.core.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tp.firebase.tp.ui.ChatScreenViewModel
import com.tp.firebase.tp.ui.LoginViewModel
import com.tp.firebase.tp.ui.screen.ChatScreen
import com.tp.firebase.tp.ui.screen.HomeScreen
import com.tp.firebase.tp.ui.screen.InitialScreen
import com.tp.firebase.tp.ui.screen.LoginScreen
import com.tp.firebase.tp.ui.screen.SignUpScreen

@Composable
fun NavigationWrapper(
    modifier: Modifier,
    navController: NavHostController,
    auth: FirebaseAuth,
    db: FirebaseFirestore,
    chatScreenViewModel: ChatScreenViewModel,
    loginViewModel: LoginViewModel
) {

    NavHost(
        navController = navController,
        startDestination = if (auth.currentUser != null) "home" else ("initial")
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
                navigateToChat = { navController.navigate("home") },
                navigateBack = { navController.popBackStack() }
            )

        }

        composable("signUp") {
            SignUpScreen(
                auth = auth,
                navigateToChat = { navController.navigate("home") },
                navigateBack = { navController.popBackStack() })


        }

        composable("home") {
            HomeScreen(
                username = loginViewModel.username.collectAsState().value,
                onNavigateBack = { navController.navigate("initial") },
                onNavigateToChat = { navController.navigate("chat") }
            )
            Log.d("AuthID", auth.currentUser?.uid.toString())
            Log.d(
                "Database ID",
                db.collection("usuario").document(auth.currentUser?.uid.toString()).id
            )
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