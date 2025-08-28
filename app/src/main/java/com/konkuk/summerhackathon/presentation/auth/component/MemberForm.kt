package com.konkuk.summerhackathon.presentation.auth.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.konkuk.summerhackathon.core.component.DuplicateTextField
import com.konkuk.summerhackathon.core.component.FormActionButton
import com.konkuk.summerhackathon.core.component.Gender
import com.konkuk.summerhackathon.core.component.GenderSegmentedField
import com.konkuk.summerhackathon.core.component.NameTextField
import com.konkuk.summerhackathon.presentation.auth.viewmodel.DuplCheckViewModel
import com.konkuk.summerhackathon.presentation.navigation.Route
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography

data class MemberSignUpData(
    val name: String,
    val nickname: String,
    val gender: Gender,
    val userId: String,
    val password: String,
    val club: String
)

@Composable
fun MemberForm(
    modifier: Modifier = Modifier,
    onSubmit: (MemberSignUpData) -> Unit = {},
    navController: NavHostController,
    viewModel: DuplCheckViewModel = hiltViewModel(),
) {
    val clubs = listOf(
        "HAM",
        "동굴탐사회",
        "IMOK",
        "우주탐구회",
        "CES",
        "멋쟁이사자처럼",
        "IF",
        "건국금융연구회",
        "건국법학회",
        "레뮤제",
        "교지편집위원회",
        "꽁냥꽁냥",
        "다솜모아",
        "죽순회",
        "로타랙트",
        "Kunimal",
        "건국호우회",
        "큐더필릭스",
        "UNSA",
        "인액터스",
        "가날지기",
        "플루마",
        "아마농구부",
        "불소야구부",
        "FC KUNIV",
        "미식축구부",
        "TAK'KU",
        "KTC",
        "산악부",
        "태연",
        "BIKU",
        "건국검사회",
        "유스호스텔",
        "스키부",
        "눈꽃",
        "청백(유도부)",
        "수중탐사부",
        "KU:GG",
        "RIKU"
    )

    var name by rememberSaveable { mutableStateOf("") }
    val isNameValid = remember(name) { SignUpValidators.isValidName(name) }

    var nicknameInput by rememberSaveable { mutableStateOf("") }
    var nicknameConfirmed by rememberSaveable { mutableStateOf<String?>(null) }

    var gender by rememberSaveable { mutableStateOf(Gender.MALE) }

    var userId by rememberSaveable { mutableStateOf("") }
    var userIdConfirmed by rememberSaveable { mutableStateOf<String?>(null) }

    var password by rememberSaveable { mutableStateOf("") }
    var pwConfirm by rememberSaveable { mutableStateOf("") }
    var isPwValid by rememberSaveable { mutableStateOf(false) }

    val isFormValid =
        isNameValid && nicknameConfirmed != null && userIdConfirmed != null && isPwValid

    var club by rememberSaveable { mutableStateOf(clubs.first()) }

    val blockers = listOfNotNull(
        if (!isNameValid) "이름" else null,
        if (nicknameConfirmed == null) "닉네임 중복확인" else null,
        if (userIdConfirmed == null) "아이디 중복확인" else null,
        if (!isPwValid) "비밀번호 일치" else null,
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.padding(top = 17.dp))
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier.fillMaxWidth()
        ) {
            FormActionButton(
                text = "확인",
                enabled = isFormValid,
                onClick = {
                    val data = MemberSignUpData(
                        name = name,
                        nickname = nicknameConfirmed!!,
                        gender = gender,
                        userId = userId,
                        password = password,
                        club = club
                    )
                    onSubmit(data)
                    navController.navigate(Route.Login.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                        restoreState = false
                    }
                },
                modifier = Modifier,
                missingReasons = blockers
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "회원정보",
                    style = typography.SB_16,
                    color = colors.black
                )
            }

            Spacer(modifier = Modifier.height(18.dp))
            NameTextField(
                value = name,
                onValueChange = { name = it }
            )

            Spacer(modifier = Modifier.height(6.dp))
//            DuplicateTextField(
//                title = "닉네임",
//                placeholder = "닉네임을 입력하세요",
//                text = nicknameInput,
//                onTextChange = { nicknameInput = it },
//                onConfirmed = { confirmed -> nicknameConfirmed = confirmed },
//                error = "닉네임 중복 입니다",
//                serverCheck = { value ->
//                    if (value.isNotBlank()) {
//                        isCheckingNickname = true
//                        viewModel.checkNickname(value)
//                    }
//                },
//                checkState = nicknameCheckState()
//            )

            Spacer(modifier = Modifier.height(6.dp))
            GenderSegmentedField(
                selected = gender,
                onSelect = { gender = it }
            )

            Spacer(modifier = Modifier.height(16.dp))
//            DuplicateTextField(
//                title = "아이디",
//                placeholder = "아이디를 입력하세요",
//                text = userId,
//                onTextChange = { userId = it },
//                onConfirmed = { confirmed -> userIdConfirmed = confirmed },
//                error = "아이디 중복 입니다",
//                serverCheck = { value ->
//                    if (value.isNotBlank()) {
//                        isCheckingUserId = true
//                        viewModel.checkUserId(value)
//                    }
//                },
//                checkState = userIdCheckState()
//            )

            Spacer(modifier = Modifier.height(6.dp))
            PasswordFields(
                password = password,
                onPasswordChange = { password = it },
                confirmPassword = pwConfirm,
                onConfirmPasswordChange = { pwConfirm = it },
                onValidityChange = { isPwValid = it }
            )

            Spacer(modifier = Modifier.height(18.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "동아리정보",
                    style = typography.SB_16,
                    color = colors.black
                )
            }

            Spacer(modifier = Modifier.height(18.dp))
            SearchDropdownChipField(
                label = "소속 동아리",
                placeholder = "소속 동아리 검색...",
                selected = club,
                options = clubs,
                onSelected = { club = it }
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}