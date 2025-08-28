package com.konkuk.summerhackathon.presentation.clublookup.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.konkuk.summerhackathon.core.component.CampusBallTopBar
import com.konkuk.summerhackathon.core.util.noRippleClickable
import com.konkuk.summerhackathon.data.dto.response.DepartmentListResponse
import com.konkuk.summerhackathon.presentation.clublookup.viewmodel.DepartmentsViewModel
import com.konkuk.summerhackathon.presentation.navigation.Route
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography

@Composable
fun CollegeLookUpScreen(
    modifier: Modifier = Modifier,
    vm: DepartmentsViewModel = hiltViewModel(),
    onDepartmentClick: (DepartmentListResponse.Department) -> Unit = {},
    navController: NavHostController
) {
    val list by vm.departments.collectAsState()
    val loading by vm.isLoading.collectAsState()
    val error by vm.error.collectAsState()

    LaunchedEffect(Unit) { vm.loadAllDepartments() }

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
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 26.dp)
        ) {
            CampusBallTopBar()

            Spacer(modifier = Modifier.height(66.dp))
            Text(
                text = "학과 선택",
                style = typography.B_24,
                color = colors.black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(31.dp))

            when {
                loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("불러오는 중…", style = typography.M_18, color = colors.gray)
                }
                error != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("학과 목록을 불러오지 못했어요", style = typography.M_18, color = colors.gray)
                }
                list.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("학과 목록이 없습니다", style = typography.M_18, color = colors.gray)
                }
                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(list, key = { it.departmentId }) { dept ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(colors.white)
                                .height(62.dp)
                                .padding(start = 23.dp)
                                .noRippleClickable { onDepartmentClick(dept) },
                            contentAlignment = Alignment.CenterStart,
                        ) {
                            Text(
                                text = dept.departmentName,
                                color = colors.black,
                                style = typography.M_18
                            )
                        }
                    }
                }
            }
        }
    }
}
