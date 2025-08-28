package com.konkuk.summerhackathon.presentation.auth.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.konkuk.summerhackathon.R
import com.konkuk.summerhackathon.core.util.noRippleClickable
import com.konkuk.summerhackathon.presentation.auth.component.ClubRoleTabs
import com.konkuk.summerhackathon.presentation.auth.component.LeaderForm
import com.konkuk.summerhackathon.presentation.auth.component.MemberForm
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography

enum class ClubRole { Leader, Member }

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var role by rememberSaveable { mutableStateOf(ClubRole.Leader) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to Color(0xFFFFFFFF),
                        0.61f to Color(0xFFCBCBCB),
                        1.0f to Color(0xFFF0F1F7)
                    )
                )
            )
            .padding(horizontal = 21.dp)
    ) {
        Spacer(modifier = Modifier.height(21.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.img_back_arrow),
                contentDescription = "back arrow icon",
                modifier = Modifier
                    .size(14.dp)
                    .align(Alignment.CenterStart)
                    .noRippleClickable { navController.popBackStack() }
            )

            Text(
                text = "회원가입",
                modifier = Modifier.align(Alignment.Center),
                style = typography.B_24
            )
        }

        Spacer(modifier = Modifier.height(23.dp))
        ClubRoleTabs(
            selected = role,
            onSelect = { role = it }
        )

        when (role) {
            ClubRole.Leader -> LeaderForm(navController = navController)
            ClubRole.Member -> MemberForm(navController = navController)
        }
    }
}