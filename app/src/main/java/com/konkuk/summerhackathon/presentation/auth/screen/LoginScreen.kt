package com.konkuk.summerhackathon.presentation.auth.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.konkuk.summerhackathon.presentation.auth.component.IdField
import com.konkuk.summerhackathon.presentation.auth.component.PasswordField
import com.konkuk.summerhackathon.presentation.auth.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = viewModel(),
    onLoginSuccess: () -> Unit = {},
    onSignUpClick: () -> Unit = {}
) {
    val id = viewModel.id
    val pw = viewModel.pw

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TODO: 여기 상단 로고/일러스트 배치
            Spacer(Modifier.height(16.dp))

            IdField(
                value = id,
                onValueChange = viewModel::onIdChange,
                isError = viewModel.idError != null || viewModel.loginError != null,
                errorText = viewModel.idError ?: viewModel.loginError
            )

            Spacer(Modifier.height(14.dp))

            PasswordField(
                value = pw,
                onValueChange = viewModel::onPwChange,
                isError = viewModel.pwError != null || viewModel.loginError != null,
                errorText = viewModel.pwError ?: viewModel.loginError
            )

            Spacer(Modifier.height(22.dp))

            Button(
                onClick = { viewModel.login(onSuccess = onLoginSuccess) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                enabled = !viewModel.isLoading
            ) {
                Text(if (viewModel.isLoading) "로그인 중..." else "로그인")
            }

            Spacer(Modifier.height(12.dp))

            TextButton(onClick = onSignUpClick) {
                Text("회원가입")
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen()
}