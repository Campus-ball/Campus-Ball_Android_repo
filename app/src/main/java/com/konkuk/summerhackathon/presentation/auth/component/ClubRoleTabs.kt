package com.konkuk.summerhackathon.presentation.auth.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.konkuk.summerhackathon.core.util.noRippleClickable
import com.konkuk.summerhackathon.presentation.auth.screen.ClubRole
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography

@Composable
fun ClubRoleTabs(
    selected: ClubRole,
    onSelect: (ClubRole) -> Unit,
    modifier: Modifier = Modifier,
    activeColor: Color = colors.black,
    inactiveColor: Color = colors.lightgray,
    underlineThickness: Int = 4,
    labelTextStyle: TextStyle = typography.B_20,
) {
    var leaderTextPx by remember { mutableIntStateOf(0) }
    var memberTextPx by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current
    val leaderUnderline = with(density) { leaderTextPx.toDp() + 64.dp }
    val memberUnderline = with(density) { memberTextPx.toDp() + 64.dp }

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .wrapContentWidth()
                .noRippleClickable { if (selected != ClubRole.Leader) onSelect(ClubRole.Leader) }
        ) {
            Text(
                text = "동아리장",
                style = labelTextStyle,
                color = if (selected == ClubRole.Leader) activeColor else inactiveColor,
                fontWeight = if (selected == ClubRole.Leader) FontWeight.Bold else FontWeight.SemiBold,
                onTextLayout = { leaderTextPx = it.size.width }
            )
            Spacer(Modifier.height(4.dp))
            Box(
                Modifier
                    .height(underlineThickness.dp)
                    .width(leaderUnderline)
                    .background(if (selected == ClubRole.Leader) activeColor else Color.Transparent)
            )
        }

        Spacer(Modifier.width(40.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .wrapContentWidth()
                .noRippleClickable { if (selected != ClubRole.Member) onSelect(ClubRole.Member) }
        ) {
            Text(
                text = "동아리원",
                style = labelTextStyle,
                color = if (selected == ClubRole.Member) activeColor else inactiveColor,
                fontWeight = if (selected == ClubRole.Member) FontWeight.Bold else FontWeight.SemiBold,
                onTextLayout = { memberTextPx = it.size.width }
            )
            Spacer(Modifier.height(4.dp))
            Box(
                Modifier
                    .height(underlineThickness.dp)
                    .width(memberUnderline)
                    .background(if (selected == ClubRole.Member) activeColor else Color.Transparent)
            )
        }
    }
}
