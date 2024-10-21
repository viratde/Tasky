package com.tasky

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.tasky.agenda.presentation.AgendaScreen
import com.tasky.auth.presentation.login.LoginScreenRoot
import com.tasky.auth.presentation.signup.SignUpScreenRoot
import com.tasky.screens.AgendaGraph
import com.tasky.screens.AgendaScreen
import com.tasky.screens.AuthGraph
import com.tasky.screens.LoginScreen
import com.tasky.screens.SignUpScreen

@Composable
fun NavigationRoot(navController: NavHostController, isLoggedIn: Boolean) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) AgendaGraph else AuthGraph
    ) {
        authGraph(navController)
        agendaGraph(navController)
    }
}

fun NavGraphBuilder.agendaGraph(navController: NavHostController) {
    navigation<AgendaGraph>(startDestination = AgendaScreen) {
        composable<AgendaScreen> {
            AgendaScreen()
        }
    }
}

fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation<AuthGraph>(startDestination = LoginScreen) {
        composable<LoginScreen> {
            LoginScreenRoot(
                onNavigateToSignUpScreen = {
                    navController.navigate(SignUpScreen)
                },
                onLoginSuccess = {
                    navController.navigate(AgendaGraph) {
                        popUpTo<AuthGraph> {
                            inclusive = true
                        }
                    }
                },
            )
        }

        composable<SignUpScreen> {
            SignUpScreenRoot(
                onBackClick = {
                    navController.navigateUp()
                },
                onSignUpSuccess = {
                    navController.navigate(LoginScreen) {
                        popUpTo<SignUpScreen> {
                            inclusive = true
                        }
                    }
                },
            )
        }
    }
}
