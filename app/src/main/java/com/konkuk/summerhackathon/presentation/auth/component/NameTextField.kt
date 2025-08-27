package com.konkuk.summerhackathon.presentation.auth.component

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography

@Composable
fun NameTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "이름을 입력하세요",
    minLength: Int = 2,
    maxLength: Int = 10,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    minHeight: Dp = 56.dp
) {
    val nameRegex = remember { Regex("^[가-힣a-zA-Z]+$") }
    val basicInvalid = value.isNotEmpty() && !nameRegex.matches(value)
    val lengthInvalid = value.isNotEmpty() && (value.length < minLength || value.length > maxLength)
    val isError = basicInvalid || lengthInvalid
    val errorText = when {
        basicInvalid -> "잘못 된 입력 형식입니다"
        lengthInvalid -> "${minLength}~${maxLength}자로 입력하세요"
        else -> null
    }

    Column(modifier = modifier) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("이름", style = typography.EB_12, color = colors.black)
            if (errorText != null) {
                Text(errorText, style = typography.L_10, color = colors.red)
            }
        }

        Spacer(modifier = Modifier.height(5.dp))
        val border = BorderStroke(1.5.dp, if (isError) colors.red else colors.indigo)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = minHeight)
                .border(border, shape)
                .clip(shape)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            BasicTextField(
                value = value.take(maxLength),
                onValueChange = { onValueChange(it.take(maxLength)) },
                textStyle = typography.M_14.copy(color = colors.black),
                singleLine = true,
                cursorBrush = SolidColor(if (isError) colors.red else colors.indigo),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart),
                decorationBox = { inner ->
                    if (value.isEmpty()) {
                        Text(placeholder, style = typography.M_14, color = colors.gray)
                    }
                    inner()
                }
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
        Box(Modifier.fillMaxWidth()) {
            Text(
                text = "${value.length}/$maxLength",
                modifier = Modifier.align(Alignment.CenterEnd),
                style = typography.R_8,
                color = colors.gray
            )
        }
    }
}
