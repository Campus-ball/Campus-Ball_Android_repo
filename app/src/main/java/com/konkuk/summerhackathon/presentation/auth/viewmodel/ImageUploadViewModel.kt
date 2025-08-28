package com.konkuk.summerhackathon.presentation.auth.viewmodel

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.summerhackathon.data.dto.response.ImageUploadResponse
import com.konkuk.summerhackathon.domain.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

@HiltViewModel
class ImageUploadViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {

    companion object { private const val TAG = "ImageUploadVM" }

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _uploadedUrl = MutableStateFlow<String?>(null)
    val uploadedUrl: StateFlow<String?> = _uploadedUrl.asStateFlow()

    fun upload(uri: Uri, resolver: ContentResolver) {
        Log.d(TAG, "upload() 호출됨 uri=$uri")
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            runCatching {
                val part = uriToPart(uri, resolver)
                imageRepository.uploadImage(part).getOrThrow()
            }.onSuccess { res: ImageUploadResponse ->
                Log.d(TAG, "upload success: ${res.data.imgUrl}")
                _uploadedUrl.value = res.data.imgUrl
            }.onFailure { e ->
                Log.e(TAG, "upload failure", e)
                _error.value = e.message ?: "이미지 업로드에 실패했습니다."
            }

            _isLoading.value = false
        }
    }

    /** 갤러리 Uri -> MultipartBody.Part 변환 */
    private fun uriToPart(uri: Uri, resolver: ContentResolver): MultipartBody.Part {
        val mime = resolver.getType(uri) ?: "image/*"
        val fileName = queryDisplayName(resolver, uri) ?: "upload_${System.currentTimeMillis()}.png"

        // 간단/러프: 전부 메모리로 읽어서 업로드 (대용량이면 스트리밍 방식 추천)
        val bytes = resolver.openInputStream(uri)?.use { it.readBytes() }
            ?: throw IllegalStateException("이미지를 읽을 수 없습니다.")
        val body: RequestBody = bytes.toRequestBody(mime.toMediaTypeOrNull())

        return MultipartBody.Part.createFormData(
            name = "file",            // 서버 스펙: "file"
            filename = fileName,
            body = body
        )
    }

    private fun queryDisplayName(resolver: ContentResolver, uri: Uri): String? {
        val projection = arrayOf(OpenableColumns.DISPLAY_NAME)
        return resolver.query(uri, projection, null, null, null)?.use { c ->
            val idx = c.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (idx >= 0 && c.moveToFirst()) c.getString(idx) else null
        }
    }
}
