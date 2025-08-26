package com.konkuk.summerhackathon.presentation.auth.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.konkuk.summerhackathon.R

@Composable
private fun AuthTextFieldBase(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorText: String?,
    trailing: (@Composable () -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(text = label, style = MaterialTheme.typography.labelMedium)
        Spacer(Modifier.height(6.dp))
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = isError,
            placeholder = { Text(placeholder) },
            trailingIcon = trailing,
            visualTransformation = visualTransformation
        )
        if (errorText != null) {
            Spacer(Modifier.height(4.dp))
            Text(
                text = errorText, color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
fun IdField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorText: String?,
    modifier: Modifier = Modifier
) = AuthTextFieldBase(
    label = "아이디",
    placeholder = "아이디를 입력하세요",
    value = value,
    onValueChange = onValueChange,
    isError = isError,
    errorText = errorText,
    modifier = modifier
)

@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorText: String?,
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }
    AuthTextFieldBase(
        label = "비밀번호",
        placeholder = "비밀번호를 입력하세요",
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        errorText = errorText,
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        trailing = {
            IconButton(onClick = { visible = !visible }) {
                Icon(
                    painter = if (visible) painterResource(id = R.drawable.ic_home) else painterResource(
                        id = R.drawable.ic_home
                    ),
                    contentDescription = if (visible) "비밀번호 숨기기" else "비밀번호 보기"
                )
            }
        },
        modifier = modifier
    )
}