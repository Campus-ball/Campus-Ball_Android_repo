package com.konkuk.summerhackathon.presentation.auth.component

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.konkuk.summerhackathon.core.component.DuplicateTextField
import com.konkuk.summerhackathon.core.component.FormActionButton
import com.konkuk.summerhackathon.core.component.Gender
import com.konkuk.summerhackathon.core.component.GenderSegmentedField
import com.konkuk.summerhackathon.core.component.NameTextField
import com.konkuk.summerhackathon.core.component.RequiredTextField
import com.konkuk.summerhackathon.data.dto.response.CollegeListResponse
import com.konkuk.summerhackathon.data.dto.response.DepartmentListResponse
import com.konkuk.summerhackathon.presentation.auth.viewmodel.DuplCheckViewModel
import com.konkuk.summerhackathon.presentation.auth.viewmodel.ImageUploadViewModel
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography

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

@Composable
fun LeaderForm(
    modifier: Modifier = Modifier,
    onSubmit: (LeaderSignUpData) -> Unit = {},
    navController: NavHostController,
    colleges: List<CollegeListResponse.College> = emptyList(),
    departments: List<DepartmentListResponse.Department> = emptyList(),
    onCollegeSelected: (Int) -> Unit = {},
    viewModel: DuplCheckViewModel = hiltViewModel(),
    uploadVm: ImageUploadViewModel = hiltViewModel()
) {

    val isNicknameValid by viewModel.isNicknameValid.collectAsState()
    val isUserIdValid by viewModel.isUserIdValid.collectAsState()
    val vmError by viewModel.errorMessage.collectAsState()

    var isCheckingNickname by remember { mutableStateOf(false) }
    var isCheckingUserId by remember { mutableStateOf(false) }

    val nicknameState by viewModel.nicknameState.collectAsStateWithLifecycle()
    val userIdState by viewModel.userIdState.collectAsStateWithLifecycle()

    /*    //todo: 아래와 같이 뷰모델 연결
        viewModel.checkNickname(nickname = "닉네임")
        viewModel.checkUserId(userId = "유저 아이디")*/

    val context = LocalContext.current

    val uploadedUrl by uploadVm.uploadedUrl.collectAsState()
    val uploading by uploadVm.isLoading.collectAsState()
    val uploadError by uploadVm.error.collectAsState()

    // 포토피커 (13+) + GetContent 폴백
    val pickVisual = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        Log.d("ImageUploadVM", "PickVisualMedia result uri=$uri")
        if (uri != null) {
            logUriInfo(context, uri)
            uploadVm.upload(uri, context.contentResolver)
        }
    }
    val getContent = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        Log.d("ImageUploadVM", "GetContent result uri=$uri")
        if (uri != null) {
            logUriInfo(context, uri)
            uploadVm.upload(uri, context.contentResolver)
        }
    }

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

    var contact by rememberSaveable { mutableStateOf<String?>(null) }

    var clubName by rememberSaveable { mutableStateOf<String?>(null) }

    var clubIntro by rememberSaveable { mutableStateOf<String?>(null) }

    var selectedCollegeId by rememberSaveable { mutableStateOf<Int?>(null) }
    val selectedCollege = colleges.firstOrNull { it.collegeId == selectedCollegeId }

    var department by rememberSaveable { mutableStateOf("") }

    var clubLogoUriStr by rememberSaveable { mutableStateOf<String?>(null) }

    var kakaoOpenChatLink by rememberSaveable { mutableStateOf<String?>(null) }

    // 업로드 성공 시 폼에 URL 반영
    LaunchedEffect(uploadedUrl) { uploadedUrl?.let { clubLogoUriStr = it } }

    LaunchedEffect(colleges) {
        if (selectedCollegeId == null && colleges.isNotEmpty()) {
            selectedCollegeId = colleges.first().collegeId
            onCollegeSelected(selectedCollegeId!!)
        }
    }

    LaunchedEffect(selectedCollegeId) {
        department = ""
    }

    LaunchedEffect(departments) {
        if (department.isBlank() && departments.isNotEmpty()) {
            department = departments.first().departmentName
        }
    }

    val isFormValid =
        isNameValid && nicknameConfirmed != null && userIdConfirmed != null && isPwValid && clubName != null && contact != null && kakaoOpenChatLink != null && clubIntro != null

    val blockers = listOfNotNull(
        if (!isNameValid) "이름" else null,
        if (nicknameConfirmed == null) "닉네임 중복확인" else null,
        if (userIdConfirmed == null) "아이디 중복확인" else null,
        if (!isPwValid) "비밀번호 일치" else null,
        if (clubName == null) "동아리명" else null,
        if (contact == null) "연락처" else null,
        if (clubIntro == null) "동아리소개" else null,
        if (kakaoOpenChatLink == null) "오픈채팅 링크" else null
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
                enabled = isFormValid && selectedCollegeId != null,
                onClick = {
                    val data = LeaderSignUpData(
                        name = name,
                        nickname = nicknameConfirmed!!,
                        gender = gender,
                        userId = userId,
                        password = password,
                        contact = contact!!,
                        clubName = clubName!!,
                        clubIntro = clubIntro!!,
                        university = selectedCollege?.collegeName ?: "",
                        department = department,
                        clubLogoUri = clubLogoUriStr,
                        kakaoOpenChatLink = kakaoOpenChatLink!!
                    )
                    onSubmit(data)
                },
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
            DuplicateTextField(
                title = "닉네임",
                error = "닉네임 중복 입니다",
                text = nicknameInput,
                onTextChange = {
                    nicknameInput = it
                    nicknameConfirmed = null
                    viewModel.resetNickname()            // ← 입력 바뀌면 즉시 Idle
                },
                onConfirmed = { confirmed -> nicknameConfirmed = confirmed },
                serverCheck = { value -> viewModel.checkNickname(value) }, // 클릭 즉시 Checking
                checkState = nicknameState
            )

            Spacer(modifier = Modifier.height(6.dp))
            GenderSegmentedField(
                selected = gender,
                onSelect = { gender = it }
            )

            Spacer(modifier = Modifier.height(16.dp))
            DuplicateTextField(
                title = "아이디",
                error = "아이디 중복 입니다",
                text = userId,
                onTextChange = {
                    userId = it
                    userIdConfirmed = null
                    viewModel.resetUserId()
                },
                onConfirmed = { confirmed -> userIdConfirmed = confirmed },
                serverCheck = { value -> viewModel.checkUserId(value) },
                checkState = userIdState
            )

            Spacer(modifier = Modifier.height(6.dp))
            PasswordFields(
                password = password,
                onPasswordChange = { password = it },
                confirmPassword = pwConfirm,
                onConfirmPasswordChange = { pwConfirm = it },
                onValidityChange = { isPwValid = it }
            )

            Spacer(modifier = Modifier.height(16.dp))
            RequiredTextField(
                label = "연락처",
                value = contact,
                onValueChange = { contact = it },
                placeholder = "연락처를 입력하세요"
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

            Spacer(modifier = Modifier.height(20.dp))
            RequiredTextField(
                label = "동아리명",
                value = clubName,
                onValueChange = { clubName = it },
                placeholder = "동아리명을 입력하세요"
            )

            Spacer(modifier = Modifier.height(16.dp))
            IntroTextArea(
                value = clubIntro,
                onValueChange = { clubIntro = it }
            )

            Spacer(modifier = Modifier.height(6.dp))
            SearchDropdownChipField(
                label = "소속 대학",
                selected = selectedCollege?.collegeName ?: "",
                options = colleges.map { it.collegeName },
                onSelected = { name ->
                    val id = colleges.find { it.collegeName == name }?.collegeId
                    selectedCollegeId = id
                    if (id != null) onCollegeSelected(id)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            SearchDropdownChipField(
                label = "소속 학과",
                placeholder = "학과 검색…",
                selected = department,
                options = departments.map { it.departmentName },
                onSelected = { department = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            LogoPickerField(
                value = clubLogoUriStr,
                onValueChange = { picked ->
                    Log.d("ImageUploadVM", "LogoPickerField onValueChange=$picked")
                    clubLogoUriStr = picked                      // 화면 표시용
                    picked?.let { uriStr ->
                        // 여기서 바로 업로드 트리거
                        runCatching { Uri.parse(uriStr) }.getOrNull()?.let { uri ->
                            uploadVm.upload(uri, context.contentResolver)
                        }
                    }
                }
            )


            Spacer(modifier = Modifier.height(16.dp))
            RequiredTextField(
                label = "카카오톡 오픈채팅",
                value = kakaoOpenChatLink,
                onValueChange = { kakaoOpenChatLink = it },
                placeholder = "카카오톡 오픈채팅 링크를 입력하세요"
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

private fun logUriInfo(context: Context, uri: Uri) {
    try {
        val cr = context.contentResolver
        val type = cr.getType(uri)
        var name: String? = null
        var size: Long? = null
        cr.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE), null, null, null)
            ?.use { c ->
                if (c.moveToFirst()) {
                    val nameIdx = c.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    val sizeIdx = c.getColumnIndex(OpenableColumns.SIZE)
                    if (nameIdx >= 0) name = c.getString(nameIdx)
                    if (sizeIdx >= 0) size = c.getLong(sizeIdx)
                }
            }
        Log.d("ImageUploadVM", "Picked uri=$uri, mime=$type, name=$name, size=$size")
    } catch (t: Throwable) {
        Log.e("ImageUploadVM", "logUriInfo error", t)
    }
}
