package com.konkuk.summerhackathon.presentation.clublookup.component

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.konkuk.summerhackathon.R
import com.konkuk.summerhackathon.ui.theme.defaultCampusBallColors
import com.konkuk.summerhackathon.ui.theme.defaultCampusBallTypography

@Composable
fun ClubLookUpCard(
    modifier: Modifier = Modifier,
    clubName: String,
    clubIcon: Int = R.drawable.ic_launcher_background,
    universityAndMajor: String,
    onAccept: () -> Unit,
    onDecline: () -> Unit,
    isRandomMatching: Boolean = false,
) {
    val noticeColor =
        if (isRandomMatching) defaultCampusBallColors.crimson else defaultCampusBallColors.mediumblue
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
                    .background(
                        color = Color(0xFFFDFDFD),
                        shape = RoundedCornerShape(size = 25.dp)
                    )
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
                            color = defaultCampusBallColors.likeblack,
                            style = defaultCampusBallTypography.SB_16
                        )
                        Spacer(Modifier.height(11.dp))
                        Text(
                            text = universityAndMajor,
                            color = defaultCampusBallColors.likeblack,
                            style = defaultCampusBallTypography.M_14
                        )
                        Spacer(Modifier.height(11.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(21.dp)
                                    .widthIn(min = 75.dp)
                                    .clickable {
                                        onAccept()
                                    }
                                    .background(
                                        defaultCampusBallColors.skyblue,
                                        RoundedCornerShape(20.dp)
                                    )
                            ) {
                                Text(
                                    "수락", style = defaultCampusBallTypography.M_8,
                                    fontSize = 10.sp,
                                    color = defaultCampusBallColors.white,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                            Spacer(Modifier.width(9.dp))
                            Box(
                                modifier = Modifier
                                    .height(21.dp)
                                    .widthIn(min = 75.dp)
                                    .clickable {
                                        onDecline()
                                    }
                                    .background(
                                        defaultCampusBallColors.salmon,
                                        RoundedCornerShape(20.dp)
                                    )
                            ) {
                                Text(
                                    "거절", style = defaultCampusBallTypography.M_8,
                                    fontSize = 10.sp,
                                    color = defaultCampusBallColors.black,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
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
                    style = defaultCampusBallTypography.SB_16,
                    color = Color.White,
                    fontSize = 11.sp,
                )
            }
        }
    }
}

@Preview()
@Composable
private fun ClubMatchCardPreview() {
    ClubLookUpCard(
        clubName = "동아리 이름1",
        universityAndMajor = "건국대학교 컴퓨터공학부",
        onAccept = {},
        onDecline = {},
        isRandomMatching = false
    )
}
