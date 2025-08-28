package com.konkuk.summerhackathon.presentation.settings.screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.konkuk.summerhackathon.core.component.CampusBallTopBar
import com.konkuk.summerhackathon.core.component.DuplicateTextField
import com.konkuk.summerhackathon.core.component.Gender
import com.konkuk.summerhackathon.core.component.GenderSegmentedField
import com.konkuk.summerhackathon.core.component.NameTextField
import com.konkuk.summerhackathon.core.component.RequiredTextField
import com.konkuk.summerhackathon.core.util.noRippleClickable
import com.konkuk.summerhackathon.presentation.auth.component.SignUpValidators
import com.konkuk.summerhackathon.presentation.navigation.Route
import com.konkuk.summerhackathon.presentation.settings.component.ConfirmModalBox
import com.konkuk.summerhackathon.presentation.settings.component.OutlinePillButton
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography

data class MyAccountUi(
    val club: String = "",
    val name: String = "",
    val nickname: String = "",
    val gender: String = "",
    val contact: String = "",
    val kakaoOpenChat: String = ""
)

@Composable
fun SettingsScreen(
    ui: MyAccountUi,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var saved by remember { mutableStateOf(ui) }

    var isEditMode by remember { mutableStateOf(false) }
    var isFormValid by remember { mutableStateOf(false) }
    var draft by remember(saved) { mutableStateOf(saved) }

    var showDone by remember { mutableStateOf(false) }

    var showLogout by remember { mutableStateOf(false) }
    var showOut by remember { mutableStateOf(false) }

    LaunchedEffect(showDone) {
        if (showDone) {
            kotlinx.coroutines.delay(2000)
            showDone = false
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
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
                .padding(horizontal = 20.dp),
        ) {
            CampusBallTopBar()

            Spacer(modifier = Modifier.height(66.dp))
            Text(
                text = "나의 계정 정보",
                style = typography.B_24,
            )

            val label = if (isEditMode) "완료" else "수정"
            val enabled = !isEditMode || (isEditMode && isFormValid)
            val highlighted = isEditMode && isFormValid

            Spacer(modifier = Modifier.height(7.dp))
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinePillButton(
                    text = label,
                    enabled = enabled,
                    highlighted = highlighted,
                    onClick = {
                        if (!isEditMode) {
                            draft = saved
                            isEditMode = true
                        } else {
                            saved = draft
                            isEditMode = false
                            showDone = true
                        }
                    },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Crossfade(
                    targetState = isEditMode,
                    modifier = Modifier
                        .fillMaxSize()
                ) { edit ->
                    if (edit) {
                        ActiveForm(
                            ui = draft,
                            onDraftChange = { draft = it },
                            onValidityChange = { isFormValid = it }
                        )
                    } else {
                        InactiveForm(
                            ui = saved,
                            onShowChange = { showLogout = it },
                            onShowOutChange = { showOut = it }
                        )
                    }
                }
            }
        }
    }
    if (showDone) {
        DoneOverlay(
            text = "수정 완료",
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f)
        )
    }
    ConfirmModalBox(
        visible = showLogout,
        message = "로그아웃 하시겠습니까?",
        leftText = "취소",
        rightText = "확인",
        onLeft  = { showLogout = false },
        onRight = { navController.navigate(Route.Login.route) },
        onDismiss = { showLogout = false }
    )
    ConfirmModalBox(
        visible = showOut,
        message = "정말로 탈퇴 하시겠습니까?",
        leftText = "취소",
        rightText = "확인",
        onLeft  = { showOut = false },
        onRight = { navController.navigate(Route.Login.route) },
        onDismiss = { showOut = false }
    )
}

@Composable
private fun ActiveForm(
    ui: MyAccountUi,
    modifier: Modifier = Modifier,
    onDraftChange: (MyAccountUi) -> Unit,
    onValidityChange: (Boolean) -> Unit
) {
    var name by rememberSaveable(ui.name) { mutableStateOf(ui.name) }
    var nicknameInput by rememberSaveable(ui.nickname) { mutableStateOf(ui.nickname) }
    var nicknameConfirmed by rememberSaveable(ui.nickname) { mutableStateOf<String?>(ui.nickname) }
    var gender by rememberSaveable(ui.gender) {
        mutableStateOf(if (ui.gender == "여자") Gender.FEMALE else Gender.MALE)
    }
    var contact by rememberSaveable(ui.contact) { mutableStateOf<String?>(ui.contact.ifEmpty { null }) }
    var kakaoOpenChatLink by rememberSaveable(ui.kakaoOpenChat) {
        mutableStateOf<String?>(ui.kakaoOpenChat.ifEmpty { null })
    }

    val isNameValid = remember(name) { SignUpValidators.isValidName(name) }

    fun publish() {
        val current = ui.copy(
            name = name,
            nickname = nicknameConfirmed ?: nicknameInput,
            gender = if (gender == Gender.MALE) "남자" else "여자",
            contact = contact.orEmpty(),
            kakaoOpenChat = kakaoOpenChatLink.orEmpty()
        )
        onDraftChange(current)

        val valid =
            isNameValid && nicknameConfirmed != null && !current.contact.isBlank() && !current.kakaoOpenChat.isBlank()
        onValidityChange(valid)
    }

    LaunchedEffect(Unit) { publish() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        NameTextField(
            value = name,
            onValueChange = {
                name = it
                publish()
            }
        )

        Spacer(modifier = Modifier.height(6.dp))
        DuplicateTextField(
            title = "닉네임",
            placeholder = "닉네임을 입력하세요",
            text = nicknameInput,
            onTextChange = {
                nicknameInput = it
                nicknameConfirmed = null
                publish()
            },
            onConfirmed = { confirmed ->
                nicknameConfirmed = confirmed
                publish()
            },
            dummyTakenNicknames = setOf("배고픈 하마"),
            error = "닉네임 중복 입니다"
        )

        Spacer(modifier = Modifier.height(6.dp))
        GenderSegmentedField(
            selected = gender,
            onSelect = {
                gender = it
                publish()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        RequiredTextField(
            label = "연락처",
            value = contact,
            onValueChange = {
                contact = it
                publish()
            },
            placeholder = "연락처를 입력하세요"
        )

        Spacer(modifier = Modifier.height(16.dp))
        RequiredTextField(
            label = "카카오톡 오픈채팅",
            value = kakaoOpenChatLink,
            onValueChange = {
                kakaoOpenChatLink = it
                publish()
            },
            placeholder = "카카오톡 오픈채팅 링크를 입력하세요"
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
private fun InactiveForm(
    ui: MyAccountUi,
    onShowChange: (Boolean) -> Unit,
    onShowOutChange: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
    ) {
        DisabledField(label = "동아리 소속", value = ui.club)
        Spacer(modifier = Modifier.height(19.dp))
        DisabledField(label = "이름", value = ui.name)
        Spacer(modifier = Modifier.height(19.dp))
        DisabledField(label = "닉네임", value = ui.nickname)
        Spacer(modifier = Modifier.height(19.dp))
        DisabledField(label = "성별", value = ui.gender)
        Spacer(modifier = Modifier.height(19.dp))
        DisabledField(label = "연락처", value = ui.contact)
        Spacer(modifier = Modifier.height(19.dp))
        DisabledField(label = "카카오톡 오픈채팅", value = ui.kakaoOpenChat)

        Spacer(modifier = Modifier.height(31.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "로그아웃",
                style = typography.M_14,
                color = colors.lightgray,
                modifier = Modifier
                    .noRippleClickable { onShowChange(true) }
            )
        }

        Spacer(modifier = Modifier.height(22.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "탈퇴",
                style = typography.M_14,
                color = colors.lightgray,
                modifier = Modifier
                    .noRippleClickable { onShowOutChange(true) }
            )
        }

        Spacer(modifier = Modifier.height(37.dp))
    }
}

@Composable
private fun DisabledField(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = typography.EB_12,
            color = colors.lightgray
        )
        Spacer(modifier = Modifier.height(5.dp))
        Box(
            modifier = Modifier
                .height(41.dp)
                .fillMaxWidth()
                .background(color = colors.white, shape = RoundedCornerShape(10.dp))
                .border(width = 1.dp, color = colors.gray, shape = RoundedCornerShape(10.dp))
                .padding(start = 11.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = value,
                style = typography.M_14,
                color = colors.lightgray
            )
        }
    }
}

@Composable
private fun DoneOverlay(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                enabled = true,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {},
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .matchParentSize()
                .padding(0.dp)
                .background(Color.Black.copy(alpha = 0.45f))
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 68.dp)
                .height(41.dp)
                .background(color = colors.black, shape = RoundedCornerShape(15.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = typography.EB_12,
                color = colors.white
            )
        }
    }
}

