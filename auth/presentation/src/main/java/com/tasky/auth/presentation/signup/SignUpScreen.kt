package com.tasky.auth.presentation.signup

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.TaskyWhite
import com.tasky.core.presentation.designsystem.ui.inter
import org.koin.androidx.compose.koinViewModel


@Composable
fun SignUpScreenRoot(
    viewModel: SignUpViewModel = koinViewModel(),
    onBack: () -> Unit,
    onNavigateToAgendaScreen: () -> Unit
) {

    SignUpScreen(
        state = viewModel.state,
        onAction = { action ->
            if (action == SignUpAction.OnBack) {
                onBack()
            }
            viewModel.onAction(action)
        }
    )

}

@Composable
fun SignUpScreen(
    state: SignUpState,
    onAction: (SignUpAction) -> Unit
) {

    TaskyScaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(TaskyBlack)
        ) {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.create_your_account),
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
                    value = state.name,
                    onValueChange = {
                        onAction(SignUpAction.OnNameChange(it))
                    },
                    placeHolder = stringResource(id = R.string.name),
                    endIcon = if (state.isValidName) ({
                        TaskySuccessIcon()
                    }) else null
                )

                Spacer(modifier = Modifier.height(16.dp))

                TaskyTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = state.email,
                    onValueChange = {
                        onAction(SignUpAction.OnEmailChange(it))
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
                        onAction(SignUpAction.OnPasswordChange(it))
                    },
                    isVisible = state.isPasswordVisible,
                    onToggleVisibility = {
                        onAction(SignUpAction.OnTogglePasswordVisibility)
                    },
                    placeHolder = stringResource(id = R.string.password),
                )

                Spacer(modifier = Modifier.height(16.dp))

                TaskyButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = stringResource(id = R.string.sign_up),
                    onClick = {
                        onAction(SignUpAction.OnSignUp)
                    }
                )

                Spacer(modifier = Modifier.weight(1f))
            }

        }
    }
}


@Preview
@Composable
private fun SignUpScreenPreview() {
    TaskyTheme {
        SignUpScreen(
            state = SignUpState(),
            onAction = {

            }
        )
    }
}