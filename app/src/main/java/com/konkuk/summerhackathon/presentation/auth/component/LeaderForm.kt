package com.konkuk.summerhackathon.presentation.auth.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


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
    val universities = listOf("건국대학교", "서울대학교", "연세대학교", "고려대학교", "성균관대학교")
    val departmentsMap = mapOf(
        "건국대학교" to listOf("컴퓨터공학부", "전자공학부", "경영학과", "수학과"),
        "서울대학교" to listOf("컴퓨터공학부", "전기정보공학부", "경영학과"),
        "연세대학교" to listOf("컴퓨터과학과", "전자공학과", "경영학과"),
        "고려대학교" to listOf("컴퓨터학과", "전기전자공학부", "경영학과"),
        "성균관대학교" to listOf("소프트웨어학과", "전자전기공학과", "경영학과"),
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

    var university by rememberSaveable { mutableStateOf(universities.first()) }
    var department by rememberSaveable { mutableStateOf(departmentsMap[universities.first()]!!.first()) }

    var contact by rememberSaveable { mutableStateOf<String?>(null) }
    var clubName by rememberSaveable { mutableStateOf<String?>(null) }
    var clubIntro by rememberSaveable { mutableStateOf("") }

    var clubLogoUriStr by rememberSaveable { mutableStateOf<String?>(null) }
    var kakaoOpenChatLink by rememberSaveable { mutableStateOf<String?>(null) }

    val deptOptions = departmentsMap[university].orEmpty()
    LaunchedEffect(university) {
        if (department !in deptOptions) {
            department = deptOptions.firstOrNull() ?: department
        }
    }

    val isFormValid =
        isNameValid && nicknameConfirmed != null && userIdConfirmed != null && isPwValid && clubName != null && contact != null && kakaoOpenChatLink != null

    val blockers = listOfNotNull(
        if (!isNameValid) "이름" else null,
        if (nicknameConfirmed == null) "닉네임 중복확인" else null,
        if (userIdConfirmed == null) "아이디 중복확인" else null,
        if (!isPwValid) "비밀번호 일치" else null,
        if (clubName == null) "동아리명" else null,
        if (contact == null) "연락처" else null,
        if (kakaoOpenChatLink == null) "오픈채팅 링크" else null
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        FormActionButton(
            text = "확인",
            enabled = isFormValid,
            onClick = {
                val data = LeaderSignUpData(
                    name = name,
                    nickname = nicknameConfirmed!!,
                    gender = gender,
                    userId = userId,
                    password = password,
                    contact = contact!!,
                    clubName = clubName!!,
                    clubIntro = clubIntro,
                    university = university,
                    department = department,
                    clubLogoUri = clubLogoUriStr,
                    kakaoOpenChatLink = kakaoOpenChatLink!!
                )
                onSubmit(data)
            },
            modifier = Modifier,
            missingReasons = blockers
        )

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

        SearchDropdownChipField(
            label = "소속 대학",
            selected = university,
            options = universities,
            onSelected = { university = it }
        )

        SearchDropdownChipField(
            label = "소속 학과",
            placeholder = "학과 검색…",
            selected = department,
            options = deptOptions,
            onSelected = { department = it }
        )

        GenderSegmentedField(
            selected = gender,
            onSelect = { gender = it }
        )

        IntroTextArea(
            value = clubIntro,
            onValueChange = { clubIntro = it }
        )

        LogoPickerField(
            value = clubLogoUriStr,
            onValueChange = { clubLogoUriStr = it }
        )

        RequiredTextField(
            label = "동아리명",
            value = clubName,
            onValueChange = { clubName = it },
            placeholder = "동아리명을 입력하세요"
        )

        RequiredTextField(
            label = "연락처",
            value = contact,
            onValueChange = { contact = it },
            placeholder = "연락처를 입력하세요"
        )

        RequiredTextField(
            label = "카카오톡 오픈채팅",
            value = kakaoOpenChatLink,
            onValueChange = { kakaoOpenChatLink = it },
            placeholder = "카카오톡 오픈채팅 링크를 입력하세요"
        )
    }
}
