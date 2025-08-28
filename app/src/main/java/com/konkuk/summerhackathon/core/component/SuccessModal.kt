package com.konkuk.summerhackathon.core.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.konkuk.summerhackathon.R
import com.konkuk.summerhackathon.core.util.noRippleClickable
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography
import com.konkuk.summerhackathon.ui.theme.defaultCampusBallColors
import com.konkuk.summerhackathon.ui.theme.defaultCampusBallTypography

@Composable
fun SuccessModal(
    modifier: Modifier = Modifier,
    value: String = "경기 신청에 성공하였습니다!",
    buttonValue: String = "홈화면으로 이동",
    value2: String? = null,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .noRippleClickable {}
            .background(color = Color(0x60000000)), contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp)
                .heightIn(min = 320.dp)
                .background(color = colors.white, shape = RoundedCornerShape(size = 16.dp))
        ) {
            Column(
                Modifier
                    .padding(top = 43.75.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    modifier = Modifier.size(52.5.dp),
                    painter = painterResource(id = R.drawable.ic_success_mark),
                    contentDescription = "",
                )
                Spacer(modifier = Modifier.height(11.15.dp))

                Text(
                    text = "SUCCESS",
                    style = typography.B_24,
                    color = colors.green,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(13.dp))

                if(value2 != null){
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            text = value,
                            style = typography.SB_16.copy(fontSize = 18.sp),
                            color = colors.likeblack,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = value2,
                            style = typography.R_15,
                            color = colors.likeblack,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                        )
                    }
                }else{
                    Text(
                        text = value,
                        style = typography.SB_16.copy(fontSize = 18.sp),
                        color = colors.likeblack,
                        textAlign = TextAlign.Center,
                    )
                }
            }
            Box(
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp, vertical = 36.dp)
                    .height(48.dp)
                    .background(color = colors.black, shape = RoundedCornerShape(size = 8.dp))
                    .clip(RoundedCornerShape(size = 8.dp))
                    .clickable {
                        onClick()
                    }
            ) {
                Text(
                    text = buttonValue,
                    color = defaultCampusBallColors.white,
                    style = defaultCampusBallTypography.B_17.copy(fontSize = 16.sp),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun SuccessModalPreview() {
    SuccessModal()
}