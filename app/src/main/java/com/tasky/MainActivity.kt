package com.tasky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val authViewModel by viewModel<AuthViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                authViewModel.state.isCheckingAuth
            }
        }
        setContent {
            TaskyTheme {
                val navController = rememberNavController()
                NavigationRoot(
                    navController = navController,
                    isLoggedIn = authViewModel.state.isLoggedIn
                )
            }
        }

    }
}
