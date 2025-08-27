package com.konkuk.summerhackathon.presentation.clublookup.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.konkuk.summerhackathon.R
import com.konkuk.summerhackathon.core.component.CampusBallTopBar
import com.konkuk.summerhackathon.presentation.clublookup.component.SearchBar
import com.konkuk.summerhackathon.presentation.clublookup.viewmodel.ClubLookUpViewModel
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography

@Composable
fun ClubLookUpScreen(
    modifier: Modifier = Modifier,
    viewModel: ClubLookUpViewModel = hiltViewModel(),
    onUniversityClick: (String) -> Unit = {}
) {
    val query = viewModel.query
    val universities = viewModel.universities

    val filtered = universities.filter {
        it.contains(query.trim(), ignoreCase = true)
    }

    Box(
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
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 26.dp)
        ) {
            CampusBallTopBar()

            Spacer(modifier = Modifier.height(66.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "대학교 선택 -",
                    style = typography.B_24,
                    color = colors.black
                )
                Text(
                    text = "축구",
                    style = typography.B_24,
                    color = colors.skyblue
                )
            }

            Spacer(modifier = Modifier.height(23.dp))
            SearchBar(
                value = query,
                onValueChange = viewModel::onQueryChange,
                placeholder = "검색창",
                leadingIconRes = R.drawable.img_search
            )


            Spacer(Modifier.height(31.dp))
            if (filtered.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "검색 결과가 없습니다",
                        color = colors.gray,
                        style = typography.M_18
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filtered, key = { it }) { name ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(colors.white)
                                .height(62.dp)
                                .padding(start = 23.dp)
                                .clickable { onUniversityClick(name) },
                            contentAlignment = Alignment.CenterStart,
                        ) {
                            Text(text = name, color = colors.black, style = typography.M_18)
                        }

                    }
                }
            }
        }
    }
}
