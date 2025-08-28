package com.konkuk.summerhackathon.core.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.konkuk.summerhackathon.core.util.noRippleClickable
import com.konkuk.summerhackathon.presentation.auth.viewmodel.CheckState
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography

@Composable
fun DuplicateTextField(
    title: String,
    error: String,                    // 서버에서 "중복" 결과일 때 보여줄 기본 문구
    text: String,
    onTextChange: (String) -> Unit,
    onConfirmed: (String?) -> Unit,   // 서버 결과에 따라 최종 확정/취소
    serverCheck: (String) -> Unit,    // 서버 검사 트리거(필수)
    checkState: CheckState,           // 서버 상태에 따른 UI 표시
    modifier: Modifier = Modifier,
    placeholder: String = "값을 입력하세요",
    minLength: Int = 1,
    maxLength: Int = 10,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    minHeight: Dp = 56.dp
) {
    val ruleOk = text.isEmpty() || text.matches(Regex("^[가-힣a-zA-Z0-9 ]+$"))
    val lengthOk = text.isEmpty() || text.length in minLength..maxLength
    val canCheck = text.isNotBlank() && ruleOk && lengthOk

    // --- 상태 → 헬퍼/에러 텍스트 계산
    val (isError, isSuccess, helperTextRaw) = when (checkState) {
        is CheckState.Checking -> Triple(false, false, "확인 중…")
        is CheckState.Available -> Triple(false, true, "사용 가능합니다")
        is CheckState.Unavailable -> Triple(true, false, checkState.reason.ifBlank { error })
        is CheckState.Error -> Triple(true, false, checkState.message)
        CheckState.Idle -> Triple(false, false, null)
    }

    // --- 테두리/커서 애니메이션 컬러
    val targetBorderColor = when {
        isError -> colors.red
        else -> colors.indigo
    }
    val borderColor by animateColorAsState(
        targetValue = targetBorderColor,
        animationSpec = tween(durationMillis = 150),
        label = "borderColor"
    )

    val helperColor: Color? = when {
        isError -> colors.red
        isSuccess -> colors.indigo
        else -> null
    }

    // 텍스트 변경 시 이전 확정 해제(부모에 알림)
    fun resetConfirm() = onConfirmed(null)

    // ★ 핵심: 서버 결과가 바뀌면 부모 확정값도 즉시 갱신 → 즉각 재컴포지션
    LaunchedEffect(checkState, text) {
        when (checkState) {
            is CheckState.Available -> onConfirmed(text.trim())
            is CheckState.Unavailable,
            is CheckState.Error,
            CheckState.Checking,
            CheckState.Idle -> onConfirmed(null)
        }
    }

    Column(modifier = modifier) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, style = typography.EB_12, color = colors.black)

            // 헬퍼 텍스트 애니메이션 표시(바뀌는 즉시 시각적 반응)
            AnimatedContent(
                targetState = helperTextRaw,
                transitionSpec = { fadeIn(tween(120)) togetherWith fadeOut(tween(120)) },
                label = "helperText"
            ) { helperText ->
                if (helperText != null && helperColor != null) {
                    Text(helperText, style = typography.L_10, color = helperColor)
                } else {
                    Spacer(Modifier) // 자리 유지
                }
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        val border = BorderStroke(1.5.dp, borderColor)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = minHeight)
                .background(colors.white, shape)
                .border(border, shape)
                .clip(shape)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            BasicTextField(
                value = text.take(maxLength),
                onValueChange = {
                    val clipped = it.take(maxLength)
                    onTextChange(clipped)
                    resetConfirm() // 입력 바뀌면 확정 해제
                },
                textStyle = typography.M_14.copy(color = colors.black),
                singleLine = true,
                cursorBrush = SolidColor(borderColor),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Text
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart),
                decorationBox = { inner ->
                    if (text.isEmpty()) {
                        Text(placeholder, style = typography.M_14, color = colors.gray)
                    }
                    inner()
                }
            )

            val buttonEnabled = canCheck && checkState !is CheckState.Checking
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .noRippleClickable {
                        if (!buttonEnabled) return@noRippleClickable
                        serverCheck(text.trim()) // 클릭 즉시 ViewModel에서 Checking 상태 발행 추천
                    }
                    .width(70.dp)
                    .height(28.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .align(Alignment.CenterEnd)
                    .border(1.dp, color = colors.magenta, shape = RoundedCornerShape(20.dp))
                    .background(
                        if (buttonEnabled) colors.white else colors.white.copy(alpha = 0.5f),
                        RoundedCornerShape(20.dp)
                    )
            ) {
                Text(
                    if (checkState is CheckState.Checking) "확인중" else "중복 확인",
                    style = typography.M_14,
                    color = colors.magenta
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Box(Modifier.fillMaxWidth()) {
            Text(
                text = "${text.length}/$maxLength",
                modifier = Modifier.align(Alignment.CenterEnd),
                style = typography.R_8,
                color = colors.gray
            )
        }
    }
}
