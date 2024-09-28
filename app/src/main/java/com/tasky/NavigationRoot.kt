package com.tasky

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.tasky.auth.presentation.login.LoginScreenRoot
import com.tasky.auth.presentation.signup.SignUpScreenRoot
import com.tasky.screens.AuthGraph
import com.tasky.screens.LoginScreen
import com.tasky.screens.SignUpScreen

@Composable
fun NavigationRoot(
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = AuthGraph) {
        authGraph(navController)
    }

}


fun NavGraphBuilder.authGraph(
    navController: NavHostController
) {

    navigation<AuthGraph>(startDestination = LoginScreen) {

        composable<LoginScreen> {

            LoginScreenRoot(
                onNavigateToSignUpScreen = {
                    navController.navigate(SignUpScreen)
                }
            )

        }

        composable<SignUpScreen> {

            SignUpScreenRoot(
                onBack = {
                    navController.popBackStack()
                },
                onNavigateToAgendaScreen = {

                }
            )

        }

    }

}