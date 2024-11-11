package com.tasky

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.tasky.agenda.presentation.agenda.AgendaItemsScreenRoot
import com.tasky.agenda.presentation.agenda_item_details.AgendaItemDetailsRoot
import com.tasky.auth.presentation.login.LoginScreenRoot
import com.tasky.auth.presentation.signup.SignUpScreenRoot
import com.tasky.screens.AgendaGraph
import com.tasky.screens.AgendaItemUiScreen
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

fun NavGraphBuilder.agendaGraph(
    navController: NavHostController
) {
    navigation<AgendaGraph>(startDestination = AgendaScreen()) {
        composable<AgendaScreen> {
            AgendaItemsScreenRoot(
                onLogoutClick = {
                    navController.navigate(AuthGraph){
                        popUpTo<AgendaGraph> {
                            inclusive = true
                        }
                    }
                },
                onNavigate = { agendaItemUiType, selectedDate, agendaItemUiId,isInEditMode ->
                    navController.navigate(
                        AgendaItemUiScreen(
                            agendaItemId = agendaItemUiId,
                            agendaItemType = agendaItemUiType,
                            selectedDate = selectedDate,
                            isInEditMode = isInEditMode
                        )
                    )
                }
            )
        }
        composable<AgendaItemUiScreen> {
            val agendaItemsUiScreen = it.toRoute<AgendaItemUiScreen>()
            AgendaItemDetailsRoot(
                selectedDate = agendaItemsUiScreen.selectedDate,
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
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
                    navController.navigate(AgendaScreen(isComingFromLoginScreen = true)) {
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
