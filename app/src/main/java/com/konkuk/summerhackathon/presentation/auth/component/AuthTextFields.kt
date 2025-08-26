package com.konkuk.summerhackathon.presentation.auth.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.konkuk.summerhackathon.R
import com.konkuk.summerhackathon.core.util.noRippleClickable
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography

@Composable
private fun AuthFieldBase(
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
    Column(modifier = modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = typography.R_15,
                color = colors.white,
                modifier = Modifier.weight(1f)
            )
            if (errorText != null) {
                Text(
                    text = errorText,
                    color = colors.red,
                    style = typography.R_12,
                    maxLines = 1,
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .background(color = colors.white, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Black,
                    fontSize = 18.sp,
                    lineHeight = 18.sp
                ),
                visualTransformation = visualTransformation,
                cursorBrush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            if (value.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    style = typography.M_18.copy(lineHeight = 18.sp),
                                    color = colors.gray
                                )
                            }
                            innerTextField()
                        }

                        if (trailing != null) {
                            trailing()
                        }
                    }
                }
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
) = AuthFieldBase(
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

    AuthFieldBase(
        label = "비밀번호",
        placeholder = "비밀번호를 입력하세요",
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        errorText = errorText,
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        trailing = {
            Icon(
                painter = if (visible) painterResource(id = R.drawable.img_eye) else painterResource(
                    id = R.drawable.img_eye_un
                ),
                contentDescription = "비밀번호 보기 이미지",
                tint = colors.gray,
                modifier = Modifier
                    .size(20.dp)
                    .noRippleClickable { visible = !visible }
            )
        },
        modifier = modifier
    )
}
