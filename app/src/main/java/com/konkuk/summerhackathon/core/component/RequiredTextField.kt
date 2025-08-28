package com.konkuk.summerhackathon.core.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
fun RequiredTextField(
    label: String,
    value: String?,
    onValueChange: (String?) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    maxLength: Int? = null,
    minHeight: Dp = 56.dp,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp)
) {
    val isError = value == null

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

        val border = BorderStroke(1.5.dp, if (isError) colors.red else colors.indigo)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = minHeight)
                .background(colors.white, shape)
                .border(border, shape)
                .clip(shape)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            BasicTextField(
                value = value.orEmpty(),
                onValueChange = { new ->
                    val clipped = if (maxLength != null) new.take(maxLength) else new
                    onValueChange(if (clipped.isEmpty()) null else clipped)
                },
                singleLine = true,
                textStyle = typography.M_14.copy(color = colors.black),
                cursorBrush = SolidColor(if (isError) colors.red else colors.indigo),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Text
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart),
                decorationBox = { inner ->
                    if (value.isNullOrEmpty() && placeholder.isNotEmpty()) {
                        Text(placeholder, style = typography.M_14, color = colors.gray)
                    }
                    inner()
                }
            )
        }
    }
}
