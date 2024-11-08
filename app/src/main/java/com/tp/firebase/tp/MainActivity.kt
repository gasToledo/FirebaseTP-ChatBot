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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tp.firebase.tp.core.navigation.NavigationWrapper
import com.tp.firebase.tp.ui.ChatScreenViewModel
import com.tp.firebase.tp.ui.LoginViewModel
import com.tp.firebase.tp.ui.theme.FirebaseTPTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var navHostController: NavHostController
    private lateinit var loginScreenViewModel: LoginViewModel
    private lateinit var chatScreenViewModel: ChatScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        firebaseAnalytics = Firebase.analytics
        db = Firebase.firestore
        setContent {
            navHostController = rememberNavController()
            chatScreenViewModel = hiltViewModel<ChatScreenViewModel>()
            loginScreenViewModel = hiltViewModel<LoginViewModel>()
            FirebaseTPTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavigationWrapper(
                        modifier = Modifier.padding(innerPadding),
                        navHostController,
                        auth,
                        db,
                        chatScreenViewModel,
                        loginScreenViewModel
                    )
                }
            }
        }
    }
}
