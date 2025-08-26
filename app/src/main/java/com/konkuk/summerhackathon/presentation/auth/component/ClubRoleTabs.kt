package com.konkuk.summerhackathon.presentation.auth.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.konkuk.summerhackathon.presentation.auth.screen.ClubRole

@Composable
fun ClubRoleTabs(
    selected: ClubRole,
    onSelect: (ClubRole) -> Unit,
    modifier: Modifier = Modifier,
    activeColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onBackground,
    inactiveColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
    indicatorThickness: Int = 2,
) {
    val tabs = listOf(ClubRole.Leader, ClubRole.Member)

    TabRow(
        selectedTabIndex = tabs.indexOf(selected),
        modifier = modifier.fillMaxWidth(),
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                Modifier.tabIndicatorOffset(tabPositions[tabs.indexOf(selected)]),
                height = indicatorThickness.dp,
                color = activeColor
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = activeColor,
        divider = {}
    ) {
        tabs.forEach { role ->
            val isSelected = role == selected
            Tab(
                selected = isSelected,
                onClick = { if (!isSelected) onSelect(role) },
                selectedContentColor = activeColor,
                unselectedContentColor = inactiveColor,
                text = {
                    Text(
                        text = when (role) {
                            ClubRole.Leader -> "동아리장"
                            ClubRole.Member -> "동아리원"
                        },
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            )
        }
    }
}
