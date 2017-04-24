package com.bl_lia.kirakiratter.data.repository.datasource.status

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.value_object.Media
import com.bl_lia.kirakiratter.domain.value_object.StatusForm
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.apache.commons.io.IOUtils
import java.util.*

class ApiStatusDataStore(
        private val context: Context,
        private val statusService: StatusService
) : StatusDataStore {

    override fun post(text: String, warning: String?, sensitive: Boolean?, mediaId: String?): Single<Status> {
        val mediaIds: List<String>? =
            if (mediaId != null) {
                listOf(mediaId)
            } else {
                null
            }

        return statusService.post(
                text = text,
                warning = warning,
                sensitive = sensitive,
                mediaIds = mediaIds
        )
    }

    override fun post(statusForm: StatusForm): Single<Status> {
        val mediaIds: List<String>? =
                if (statusForm.mediaId != null) {
                    listOf(statusForm.mediaId.toString())
                } else {
                    null
                }

        return statusService.post(
                text = statusForm.status,
                warning = statusForm.spoilerText,
                inReplyTo = statusForm.inReplyToId,
                sensitive = statusForm.sensitive,
                mediaIds = mediaIds
        )
    }

    override fun uploadMedia(uri: Uri): Single<Media> {
        val content: ByteArray = IOUtils.toByteArray(context.contentResolver.openInputStream(uri))
        val mimeType: String = context.contentResolver.getType(uri)
        val fileName: String = generateFileName(mimeType)
        val requestBody: RequestBody = RequestBody.create(MediaType.parse(mimeType), content)
        val file = MultipartBody.Part.createFormData("file", fileName, requestBody)

        return statusService.uploadMedia(file)
    }

    private fun generateFileName(mimeType: String): String {
        val id = UUID.randomUUID()
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return "%s.%s".format(id.toString(), mimeTypeMap.getExtensionFromMimeType(mimeType))
    }

}