package com.konkuk.summerhackathon.presentation.auth.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.konkuk.summerhackathon.presentation.auth.component.ClubRoleTabs
import com.konkuk.summerhackathon.presentation.auth.component.LeaderForm
import com.konkuk.summerhackathon.presentation.auth.component.MemberForm

enum class ClubRole { Leader, Member }

@Composable
fun SignUpScreen(modifier: Modifier = Modifier) {
    var role by rememberSaveable { mutableStateOf(ClubRole.Leader) }

    Column(
        modifier = modifier
    ) {
        ClubRoleTabs(
            selected = role,
            onSelect = { role = it }
        )

        when (role) {
            ClubRole.Leader -> LeaderForm()
            ClubRole.Member -> MemberForm()
        }
    }
}