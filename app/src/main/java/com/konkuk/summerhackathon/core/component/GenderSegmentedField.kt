package com.konkuk.summerhackathon.core.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography
import com.konkuk.summerhackathon.core.util.noRippleClickable

enum class Gender { MALE, FEMALE }

@Composable
fun GenderSegmentedField(
    selected: Gender,
    onSelect: (Gender) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "성별",
    height: Dp = 44.dp,
    outerShape: RoundedCornerShape = RoundedCornerShape(14.dp),
    innerShape: RoundedCornerShape = RoundedCornerShape(12.dp)
) {
    Column(modifier = modifier) {
        Text(text = label, style = typography.EB_12, color = colors.black)

        Spacer(Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .border(BorderStroke(1.5.dp, colors.indigo), outerShape)
                .clip(outerShape)
                .padding(4.dp), // 안쪽 칩과 테두리 사이 여백
            verticalAlignment = Alignment.CenterVertically
        ) {
            Segment(
                text = "남자",
                selected = selected == Gender.MALE,
                onClick = { if (selected != Gender.MALE) onSelect(Gender.MALE) },
                modifier = Modifier.weight(1f),
                innerShape = innerShape
            )

            Spacer(Modifier.width(4.dp))

            Segment(
                text = "여자",
                selected = selected == Gender.FEMALE,
                onClick = { if (selected != Gender.FEMALE) onSelect(Gender.FEMALE) },
                modifier = Modifier.weight(1f),
                innerShape = innerShape
            )
        }
    }
}

@Composable
private fun Segment(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    innerShape: RoundedCornerShape
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(innerShape)
            .background(if (selected) colors.indigo.copy(alpha = 0.18f) else Color.Transparent)
            .noRippleClickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = typography.M_14,
            color = if (selected) colors.black else colors.gray
        )
    }
}
