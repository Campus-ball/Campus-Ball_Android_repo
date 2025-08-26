package com.konkuk.summerhackathon.presentation.auth.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography

// ===== 모델 =====
enum class Gender { MALE, FEMALE, OTHER }

data class LeaderSignUpData(
    val name: String,
    val nickname: String,
    val gender: Gender,
    val userId: String,
    val password: String,
    val contact: String,
    val clubName: String,
    val clubIntro: String,
    val university: String,
    val department: String,
    val clubLogoUri: String?,
    val kakaoOpenChatLink: String
)

object SignUpValidators {
    fun isValidName(
        name: String,
        minLen: Int = 2,
        maxLen: Int = 10
    ): Boolean {
        val regex = Regex("^[가-힣a-zA-Z]+$")
        return name.length in minLen..maxLen && regex.matches(name)
    }
}

// ===== 폼 =====
@Composable
fun LeaderForm(
    modifier: Modifier = Modifier,
    onSubmit: (LeaderSignUpData) -> Unit = {}
) {
    // --- 더미 데이터 ---
    val universities = listOf("건국대학교", "서울대학교", "연세대학교", "고려대학교", "성균관대학교")
    val departmentsMap = mapOf(
        "건국대학교" to listOf("컴퓨터공학부", "전자공학부", "경영학과", "수학과"),
        "서울대학교" to listOf("컴퓨터공학부", "전기정보공학부", "경영학과"),
        "연세대학교" to listOf("컴퓨터과학과", "전자공학과", "경영학과"),
        "고려대학교" to listOf("컴퓨터학과", "전기전자공학부", "경영학과"),
        "성균관대학교" to listOf("소프트웨어학과", "전자전기공학과", "경영학과"),
    )

    // --- 상태 ---
    var name by rememberSaveable { mutableStateOf("") }
    val isNameValid = remember(name) { SignUpValidators.isValidName(name) }

    // 닉네임: 입력값 / 확인 통과값 분리
    var nicknameInput by rememberSaveable { mutableStateOf("") }
    var nicknameConfirmed by rememberSaveable { mutableStateOf<String?>(null) }

    var gender by rememberSaveable { mutableStateOf(Gender.MALE) }

    var userId by rememberSaveable { mutableStateOf("") }
    var userIdConfirmed by rememberSaveable { mutableStateOf<String?>(null) }

    var password by rememberSaveable { mutableStateOf("") }
    var pwConfirm by rememberSaveable { mutableStateOf("") }
    var isPwValid by rememberSaveable { mutableStateOf(false) }

    var contact by rememberSaveable { mutableStateOf("") }
    var clubName by rememberSaveable { mutableStateOf("") }
    var clubIntro by rememberSaveable { mutableStateOf("") }
    var university by rememberSaveable { mutableStateOf(universities.first()) }
    var department by rememberSaveable { mutableStateOf(departmentsMap[universities.first()]!!.first()) }
    var clubLogoUriStr by rememberSaveable { mutableStateOf<String?>(null) }
    var kakaoOpenChatLink by rememberSaveable { mutableStateOf("") }

    // 대학 변경 시 학과 초기화
    LaunchedEffect(university) {
        departmentsMap[university]?.firstOrNull()?.let { first ->
            if (department !in (departmentsMap[university] ?: emptyList())) {
                department = first
            }
        }
    }

    // 현재 구현된 필드 기준 활성화 조건 (추가 필드 붙이면 조건 확장)
    val isFormValid = isNameValid && nicknameConfirmed != null && userIdConfirmed != null && isPwValid

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        NameTextField(
            value = name,
            onValueChange = { name = it }
        )

        DuplicateTextField(
            title = "닉네임",
            placeholder = "닉네임을 입력하세요",
            text = nicknameInput,
            onTextChange = { nicknameInput = it },
            onConfirmed = { confirmed -> nicknameConfirmed = confirmed },
            dummyTakenNicknames = setOf("배고픈 하마"),
            error = "닉네임 중복 입니다"
        )

        DuplicateTextField(
            title = "아이디",
            placeholder = "아이디를 입력하세요",
            text = userId,
            onTextChange = { userId = it },
            onConfirmed = { confirmed -> userIdConfirmed = confirmed },
            dummyTakenNicknames = setOf("hungry"),
            error = "아이디 중복 입니다"
        )

        PasswordFields(
            password = password,
            onPasswordChange = { password = it },
            confirmPassword = pwConfirm,
            onConfirmPasswordChange = { pwConfirm = it },
            onValidityChange = { isPwValid = it }
        )

        Button(
            onClick = {
                val data = LeaderSignUpData(
                    name = name,
                    nickname = nicknameConfirmed!!,
                    gender = gender,
                    userId = userId,
                    password = password,
                    contact = contact,
                    clubName = clubName,
                    clubIntro = clubIntro,
                    university = university,
                    department = department,
                    clubLogoUri = clubLogoUriStr,
                    kakaoOpenChatLink = kakaoOpenChatLink
                )
                onSubmit(data)
            },
            enabled = isFormValid,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("임시 저장", style = typography.B_17)
        }
    }
}
