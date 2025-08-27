package com.konkuk.summerhackathon.presentation.auth.component

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.konkuk.summerhackathon.core.util.noRippleClickable
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.colors
import com.konkuk.summerhackathon.ui.theme.SummerHackathonTheme.typography

@Composable
fun LogoPickerField(
    value: String?,
    onValueChange: (String?) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "동아리 로고 사진",
    placeholder: String = "파일 선택 …",
    minHeight: Dp = 56.dp,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp)
) {
    val ctx = LocalContext.current
    val TAG = "LogoPickerField"

    // Android 13+ 시스템 포토 피커
    val pickVisualLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        Log.d(TAG, "PickVisualMedia result: $uri")
        onValueChange(uri?.toString())
    }

    // 하위 버전용 SAF
    val getContentLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        Log.d(TAG, "GetContent result: $uri")
        onValueChange(uri?.toString())
    }

    fun openPicker() {
        val available = ActivityResultContracts.PickVisualMedia.isPhotoPickerAvailable(ctx)
        Log.d(TAG, "openPicker, isPhotoPickerAvailable=$available")
        if (available) {
            pickVisualLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        } else {
            getContentLauncher.launch("image/*")
        }
    }

    Column(modifier = modifier) {
        Text(text = label, style = typography.EB_12, color = colors.black)
        Spacer(Modifier.height(6.dp))

        val border = BorderStroke(1.5.dp, colors.indigo)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = minHeight)
                .border(border, shape)
                .clip(shape)
                .noRippleClickable { openPicker() }
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = value ?: placeholder,
                style = typography.M_14,
                color = if (value == null) colors.gray else colors.black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
            )
        }
    }
}
