package com.konkuk.summerhackathon.core.component

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.konkuk.summerhackathon.core.util.noRippleClickable
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography


@Composable
fun DuplicateTextField(
    title: String,
    error: String,
    text: String,
    onTextChange: (String) -> Unit,
    onConfirmed: (String?) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "닉네임을 입력하세요",
    minLength: Int = 1,
    maxLength: Int = 10,
    dummyTakenNicknames: Set<String> = setOf("배고픈 하마"),
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    minHeight: Dp = 56.dp
) {
    val ruleOk = text.isEmpty() || text.matches(Regex("^[가-힣a-zA-Z0-9 ]+$"))
    val lengthOk = text.isEmpty() || text.length in minLength..maxLength

    var checked by remember { mutableStateOf(false) }
    var available by remember { mutableStateOf(false) }

    val canCheck = text.isNotBlank() && ruleOk && lengthOk
    val isDuplicate = remember(text) {
        val normalized = text.trim()
        dummyTakenNicknames.any { it.trim().equals(normalized, ignoreCase = true) }
    }

    val showError = checked && !available && canCheck
    val showSuccess = checked && available && canCheck
    val helperText = when {
        showError -> error
        showSuccess -> "사용 가능합니다"
        else -> null
    }
    val helperColor = when {
        showError -> colors.red
        showSuccess -> colors.indigo
        else -> null
    }

    fun resetCheck() {
        if (checked || available) {
            checked = false
            available = false
            onConfirmed(null)
        }
    }

    Column(
        modifier = modifier
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, style = typography.EB_12, color = colors.black)
            if (helperText != null && helperColor != null) {
                Text(helperText, style = typography.L_10, color = helperColor)
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        val border = BorderStroke(
            1.5.dp,
            if (showError) colors.red else colors.indigo
        )

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
                value = text.take(maxLength),
                onValueChange = {
                    val clipped = it.take(maxLength)
                    onTextChange(clipped)
                    resetCheck()
                },
                textStyle = typography.M_14.copy(color = colors.black),
                singleLine = true,
                cursorBrush = SolidColor(if (showError) colors.red else colors.indigo),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Text
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart),
                decorationBox = { inner ->
                    if (text.isEmpty()) {
                        Text(placeholder, style = typography.M_14, color = colors.gray)
                    }
                    inner()
                }
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .noRippleClickable {
                        checked = true
                        if (!isDuplicate && canCheck) {
                            available = true
                            onConfirmed(text.trim())
                        } else {
                            available = false
                            onConfirmed(null)
                        }
                    }
                    .width(45.dp)
                    .height(22.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .align(Alignment.CenterEnd)
                    .border(1.dp, color = colors.magenta, shape = RoundedCornerShape(20.dp))
            ) {
                Text("중복 확인", style = typography.M_8, color = colors.magenta)
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Box(Modifier.fillMaxWidth()) {
            Text(
                text = "${text.length}/$maxLength",
                modifier = Modifier.align(Alignment.CenterEnd),
                style = typography.R_8,
                color = colors.gray
            )
        }
    }
}
