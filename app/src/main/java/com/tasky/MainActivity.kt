package com.tasky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.tasky.core.presentation.designsystem.ui.TaskyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().apply {
            // Todo Check here whether user is logged in or not
        }
        setContent {
            TaskyTheme {
                val navController = rememberNavController()
                NavigationRoot(navController = navController)
            }
        }
    }
}

