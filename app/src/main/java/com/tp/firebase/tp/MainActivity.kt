package com.tp.firebase.tp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tp.firebase.tp.core.navigation.NavigationWrapper
import com.tp.firebase.tp.ui.ChatViewModel
import com.tp.firebase.tp.ui.HomeViewModel
import com.tp.firebase.tp.ui.LoginViewModel
import com.tp.firebase.tp.ui.SignUpViewModel
import com.tp.firebase.tp.ui.theme.FirebaseTPTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var auth: FirebaseAuth

    private lateinit var navHostController: NavHostController
    private lateinit var loginScreenViewModel: LoginViewModel
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var signUpViewModel: SignUpViewModel
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        firebaseAnalytics = Firebase.analytics

        setContent {
            navHostController = rememberNavController()
            chatViewModel = hiltViewModel<ChatViewModel>()
            loginScreenViewModel = hiltViewModel<LoginViewModel>()
            signUpViewModel = hiltViewModel<SignUpViewModel>()
            homeViewModel = hiltViewModel<HomeViewModel>()

            FirebaseTPTheme(darkTheme = false, dynamicColor = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavigationWrapper(
                        modifier = Modifier.padding(innerPadding),
                        navHostController,
                        auth,
                        chatViewModel,
                        loginScreenViewModel,
                        signUpViewModel,
                        homeViewModel
                    )
                }
            }
        }
    }
}
