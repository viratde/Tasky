package com.tasky.auth.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasky.auth.presentation.R
import com.tasky.core.presentation.designsystem.components.TaskyButton
import com.tasky.core.presentation.designsystem.components.TaskyPasswordTextField
import com.tasky.core.presentation.designsystem.components.TaskyScaffold
import com.tasky.core.presentation.designsystem.components.TaskySuccessIcon
import com.tasky.core.presentation.designsystem.components.TaskyTextField
import com.tasky.core.presentation.designsystem.ui.TaskyBlack
import com.tasky.core.presentation.designsystem.ui.TaskyBlue
import com.tasky.core.presentation.designsystem.ui.TaskyTextFieldPlaceHolderColor
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.TaskyWhite
import com.tasky.core.presentation.designsystem.ui.inter
import org.koin.androidx.compose.koinViewModel


@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel = koinViewModel(),
    onNavigateToSignUpScreen: () -> Unit
) {

    LoginScreen(
        state = viewModel.state,
        onAction = { action ->
            if (action == LoginAction.OnNavigateToSignUpScreen) {
                onNavigateToSignUpScreen()
            }
            viewModel.onAction(action)
        }
    )

}

@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {

    TaskyScaffold {innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(TaskyBlack)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.welcome_back),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TaskyWhite,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = inter
                    ),
                    textAlign = TextAlign.Center
                )
            }


            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(
                        RoundedCornerShape(
                            topStart = 30.dp,
                            topEnd = 30.dp,
                        )
                    )
                    .background(TaskyWhite)
                    .padding(
                        vertical = 48.dp,
                        horizontal = 16.dp
                    )
                    .fillMaxWidth()
            ) {


                TaskyTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = state.email,
                    onValueChange = {
                        onAction(LoginAction.OnEmailChange(it))
                    },
                    placeHolder = stringResource(id = R.string.email_address),
                    endIcon = if (state.isValidEmail) ({
                        TaskySuccessIcon()
                    }) else null
                )

                Spacer(modifier = Modifier.height(16.dp))

                TaskyPasswordTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = state.password,
                    onValueChange = {
                        onAction(LoginAction.OnPasswordChange(it))
                    },
                    isVisible = state.isPasswordVisible,
                    onToggleVisibility = {
                        onAction(LoginAction.OnTogglePasswordVisibility)
                    },
                    placeHolder = stringResource(id = R.string.password),
                    error = state.isPasswordError,
                )

                Spacer(modifier = Modifier.height(16.dp))

                TaskyButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = stringResource(id = R.string.log_in),
                    onClick = {
                        onAction(LoginAction.OnLogin)
                    }
                )

                Spacer(modifier = Modifier.weight(1f))


                val annotatedString = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = TaskyTextFieldPlaceHolderColor,
                            fontFamily = inter,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    ) {
                        append(stringResource(id = R.string.donot_have_an_account) + " ")
                    }
                    withLink(
                        link = LinkAnnotation.Clickable(
                            linkInteractionListener = {
                                onAction(LoginAction.OnNavigateToSignUpScreen)
                            },
                            tag = stringResource(id = R.string.sign_up)
                        )
                    ) {
                        withStyle(
                            SpanStyle(
                                color = TaskyBlue,
                                fontFamily = inter,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                            )
                        ) {
                            append(stringResource(id = R.string.sign_up))
                        }
                    }
                }

                Text(
                    text = annotatedString,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

            }

        }
    }

}

@Preview
@Composable
private fun TaskyScreenPreview() {
    TaskyTheme {
        LoginScreen(state = LoginState()) {

        }
    }
}