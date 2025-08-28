package com.konkuk.summerhackathon.presentation.auth.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun IntroTextArea(
    value: String?,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "소개 글",
    placeholder: String = "소개 글을 입력하세요",
    maxLength: Int = 100,
    minHeight: Dp = 20.dp,
    maxHeight: Dp = 80.dp,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp)
) {
    val scroll = rememberScrollState()
    val isError = value == null
    val border = BorderStroke(1.5.dp, if (isError) colors.red else colors.indigo)

    Column(
        modifier = modifier
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, style = typography.EB_12, color = colors.black)
            if (isError) Text("필수 입력입니다", style = typography.L_10, color = colors.red)
        }

        Spacer(Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(border, shape)
                .background(colors.white, shape)
                .clip(shape)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            BasicTextField(
                value = value.orEmpty().take(maxLength),
                onValueChange = { onValueChange(it.take(maxLength)) },
                textStyle = typography.M_14.copy(color = colors.black),
                singleLine = false,
                cursorBrush = SolidColor(colors.indigo),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Text
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = minHeight, max = maxHeight)
                    .verticalScroll(scroll),
                decorationBox = { inner ->
                    if (value.isNullOrEmpty()) {
                        Text(placeholder, style = typography.M_14, color = colors.gray)
                    }
                    inner()
                }
            )
        }

        Spacer(Modifier.height(4.dp))
        Box(Modifier.fillMaxWidth()) {
            Text(
                text = "${value?.length ?: 0}/$maxLength",
                style = typography.R_8,
                color = colors.gray,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}