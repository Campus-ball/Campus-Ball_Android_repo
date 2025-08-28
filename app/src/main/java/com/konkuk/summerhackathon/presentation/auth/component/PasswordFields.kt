package com.konkuk.summerhackathon.presentation.auth.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography

@Composable
fun PasswordFields(
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    onValidityChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    placeholder1: String = "비밀번호를 입력하세요",
    placeholder2: String = "비밀번호를 다시 입력하세요",
    maxLength: Int = 20,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    minHeight: Dp = 56.dp
) {
    val isMismatch = confirmPassword.isNotEmpty() && (password != confirmPassword)
    val isValid = password.isNotEmpty() && confirmPassword.isNotEmpty() && !isMismatch

    SideEffect { onValidityChange(isValid) }

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Column {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("비밀번호", style = typography.EB_12, color = colors.black)
            }
            Spacer(Modifier.height(5.dp))
            val border1 = BorderStroke(1.5.dp, colors.indigo)
            Box(
                Modifier.fillMaxWidth().defaultMinSize(minHeight = minHeight).background(colors.white, shape)
                    .border(border1, shape).clip(shape).padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                BasicTextField(
                    value = password.take(maxLength),
                    onValueChange = { onPasswordChange(it.take(maxLength)) },
                    textStyle = typography.M_14.copy(color = colors.black),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    cursorBrush = SolidColor(colors.indigo),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterStart),
                    decorationBox = { inner ->
                        if (password.isEmpty()) {
                            Text(placeholder1, style = typography.M_14, color = colors.gray)
                        }
                        inner()
                    }
                )
            }
        }

        Column {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("비밀번호 확인", style = typography.EB_12, color = colors.black)
                if (isMismatch) Text("비밀번호가 다릅니다", style = typography.L_10, color = colors.red)
            }
            Spacer(Modifier.height(5.dp))
            val border2 = BorderStroke(1.5.dp, if (isMismatch) colors.red else colors.indigo)
            Box(
                Modifier.fillMaxWidth().defaultMinSize(minHeight = minHeight).background(colors.white, shape)
                    .border(border2, shape).clip(shape).padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                BasicTextField(
                    value = confirmPassword.take(maxLength),
                    onValueChange = { onConfirmPasswordChange(it.take(maxLength)) },
                    textStyle = typography.M_14.copy(color = colors.black),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    cursorBrush = SolidColor(if (isMismatch) colors.red else colors.indigo),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterStart),
                    decorationBox = { inner ->
                        if (confirmPassword.isEmpty()) {
                            Text(placeholder2, style = typography.M_14, color = colors.gray)
                        }
                        inner()
                    }
                )
            }
        }
    }
}
