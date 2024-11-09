package com.tp.firebase.tp.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tp.firebase.tp.ui.ChatScreenViewModel
import com.tp.firebase.tp.ui.HomeViewModel
import com.tp.firebase.tp.ui.LoginViewModel
import com.tp.firebase.tp.ui.SignUpViewModel
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
    loginViewModel: LoginViewModel,
    signUpViewModel: SignUpViewModel,
    homeViewModel: HomeViewModel,
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
                navigateToHome = { navController.navigate("home") },
                navigateBack = { navController.popBackStack() },
                viewModel = signUpViewModel
            )
        }

        composable("home") {
            HomeScreen(
                username = loginViewModel.username.collectAsState().value.toString(),
                onNavigateToChat = { navController.navigate("chat") },
                signOut = {
                    navController.navigate("initial")
                    auth.signOut()
                },
                isChatActive = homeViewModel.isChatActive.collectAsState().value,
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