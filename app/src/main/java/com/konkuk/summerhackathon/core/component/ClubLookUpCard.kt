package com.konkuk.summerhackathon.core.component

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
    clubName: String = "동아리 이름",
    clubIcon: Int = R.drawable.img_basic_icon,
    collegeAndMajor: String = "OO대학교 OOO학과",
    onClick: () -> Unit = {}
) {

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 96.dp)
                    .clip(RoundedCornerShape(size = 25.dp))
                    .background(
                        color = Color(0xFFFDFDFD),
                        shape = RoundedCornerShape(size = 25.dp)
                    )
                    .clickable { onClick() }
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
                            style = defaultCampusBallTypography.SB_16.copy(fontSize = 18.sp)
                        )
                        Spacer(Modifier.height(15.dp))
                        Text(
                            text = collegeAndMajor,
                            color = defaultCampusBallColors.likeblack,
                            style = defaultCampusBallTypography.M_14.copy(fontSize = 16.sp)
                        )
                    }
                }
            }
        }

    }
}

@Preview()
@Composable
private fun ClubLookUpCardPreview() {
    ClubLookUpCard(
        clubName = "동아리 이름1",
        collegeAndMajor = "건국대학교 컴퓨터공학부",
    )
}
