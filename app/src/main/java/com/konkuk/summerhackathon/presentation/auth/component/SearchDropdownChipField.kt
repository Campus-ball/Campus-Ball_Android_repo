package com.konkuk.summerhackathon.presentation.auth.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.konkuk.summerhackathon.core.util.noRippleClickable
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchDropdownChipField(
    label: String = "소속 대학",
    selected: String? = null,
    options: List<String>,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "대학 검색…",
    minHeight: Dp = 56.dp,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    borderColor: Color = colors.indigo
) {
    var expanded by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    LaunchedEffect(expanded) {
        if (expanded) query = ""
    }

    val filtered = remember(query, options) {
        if (query.isBlank()) options
        else options.filter { it.contains(query.trim(), ignoreCase = true) }
    }

    Column(
        modifier = modifier
    ) {
        Text(text = label, style = typography.EB_12, color = colors.black)

        Spacer(Modifier.height(5.dp))
        val stroke = BorderStroke(1.5.dp, borderColor)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = minHeight)
                .background(colors.white, shape)
                .border(stroke, shape)
                .clip(shape)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .noRippleClickable { expanded = true }
        ) {
            BasicTextField(
                value = if (expanded) query else selected.orEmpty(),
                onValueChange = { query = it },
                singleLine = true,
                textStyle = typography.M_14.copy(color = colors.black),
                cursorBrush = SolidColor(borderColor),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text
                ),
                enabled = expanded,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart),
                decorationBox = { inner ->
                    val showPlaceholder =
                        (!expanded && selected.isNullOrEmpty()) || (expanded && query.isEmpty())
                    if (showPlaceholder) {
                        Text(placeholder, style = typography.M_14, color = colors.gray)
                    }
                    inner()
                }
            )
        }

        if (expanded) {
            Spacer(Modifier.height(1.dp))
            Surface(
                tonalElevation = 0.dp,
                shadowElevation = 0.dp,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth(),
                color = colors.black.copy(0.2f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    if (filtered.isEmpty()) {
                        Box(Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
                            Text(
                                "검색 결과가 없어요",
                                style = typography.R_15,
                                color = colors.gray,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    } else {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            filtered.forEach { item ->
                                TagChip(
                                    text = item,
                                    onClick = {
                                        onSelected(item)
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TagChip(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(999.dp))
            .background(colors.indigo)
            .noRippleClickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(text = text, style = typography.B_11, color = colors.white)
    }
}
