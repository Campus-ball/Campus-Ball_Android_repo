package com.konkuk.summerhackathon.presentation.schedule.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.konkuk.summerhackathon.R
import com.konkuk.summerhackathon.core.util.noRippleClickable
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography

@Composable
fun ScheduleClubCard(
    modifier: Modifier = Modifier,
    clubName: String = "동아리 이름",
    clubIcon: Int = R.drawable.ic_launcher_background,
    universityAndMajor: String = "OO대학교 OOO학과",
    kakaoTalkLink: String = "카카오톡 링크 복사 테스트값",
    isRandomMatching: Boolean = false,
    onClickCard: () -> Unit = {}
) {
    val clipboardManager = LocalClipboardManager.current


    val noticeColor =
        if (isRandomMatching) colors.crimson else colors.mediumblue
    val noticeText =
        if (isRandomMatching) "랜덤 매칭 요청" else "친선 경기"

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 5.dp)
                    .heightIn(min = 110.dp)
                    .clip(RoundedCornerShape(size = 25.dp))
                    .background(
                        color = Color(0xFFFDFDFD),
                        shape = RoundedCornerShape(size = 25.dp)
                    )
                    .clickable {
                        onClickCard()
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = clubIcon),
                        contentDescription = "동아리 이미지",
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(65.dp),
                    )

                    Spacer(Modifier.width(19.dp))

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = clubName,
                            color = colors.likeblack,
                            style = typography.SB_16
                        )
                        Spacer(Modifier.height(11.dp))
                        Text(
                            text = universityAndMajor,
                            color = colors.likeblack,
                            style = typography.M_14
                        )
                        Spacer(Modifier.height(11.dp))
                        Box(
                            modifier = Modifier
                                .height(22.dp)
                                .widthIn(min = 148.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .clickable() {
                                    clipboardManager.setText(AnnotatedString(kakaoTalkLink))
                                }
                                .background(colors.yellow, RoundedCornerShape(5.dp))
                        ) {
                            Text(
                                "오픈채팅방 링크 복사", style = typography.M_8,
                                color = Color(0xFF8F2626),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 5.dp)
                    .clip(RoundedCornerShape(12.dp, 0.dp, 12.dp, 12.dp))
                    .background(noticeColor)
                    .widthIn(80.dp)
                    .padding(horizontal = 9.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = noticeText,
                    style = typography.SB_16,
                    color = Color.White,
                    fontSize = 11.sp,
                )
            }
        }
    }
}

@Preview()
@Composable
private fun ScheduleClubCardPreview() {
    ScheduleClubCard(
        isRandomMatching = false
    )
}

