package com.konkuk.summerhackathon.presentation.auth.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.konkuk.summerhackathon.R
import com.konkuk.summerhackathon.presentation.auth.component.IdField
import com.konkuk.summerhackathon.presentation.auth.component.LoginButton
import com.konkuk.summerhackathon.presentation.auth.component.PasswordField
import com.konkuk.summerhackathon.presentation.auth.viewmodel.AuthViewModel
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = viewModel(),
    onLoginSuccess: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val mergedError: String? =
        viewModel.idError ?: viewModel.pwError ?: viewModel.loginError

    val id = viewModel.id
    val pw = viewModel.pw

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1F4AA8))
            .padding(horizontal = 35.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Image(
                painter = painterResource(id = R.drawable.img_soccer_ball),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "CampusBall",
                style = typography.SB_14.copy(fontSize = 18.sp),
                color = colors.white
            )
        }

        Spacer(modifier = Modifier.height(80.dp))
        Image(
            painter = painterResource(id = R.drawable.img_login_logo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(293.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(50.dp))
        IdField(
            value = id,
            onValueChange = viewModel::onIdChange,
            isError = viewModel.idError != null || viewModel.loginError != null,
            errorText = mergedError
        )

        Spacer(Modifier.height(18.dp))
        PasswordField(
            value = pw,
            onValueChange = viewModel::onPwChange,
            isError = viewModel.pwError != null || viewModel.loginError != null,
            errorText = null
        )

        Spacer(Modifier.height(36.dp))
        LoginButton(
            text = if (viewModel.isLoading) "로그인 중..." else "로그인",
            enabled = !viewModel.isLoading,
            onClick = { viewModel.login(onSuccess = onLoginSuccess) }
        )

        Spacer(Modifier.height(36.dp))
        Text(
            text = "회원가입",
            style = typography.M_14,
            color = colors.white
        )

        Spacer(modifier = Modifier.height(80.dp))
    }
}