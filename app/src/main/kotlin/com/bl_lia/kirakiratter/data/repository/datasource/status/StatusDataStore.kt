package com.bl_lia.kirakiratter.data.repository.datasource.status

import android.net.Uri
import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.value_object.Media
import com.bl_lia.kirakiratter.domain.value_object.StatusForm
import io.reactivex.Single

interface StatusDataStore {

    fun post(
            text: String,
            warning: String? = null,
            sensitive: Boolean? = null,
            mediaId: String? = null
    ): Single<Status>

    fun post(statusForm: StatusForm): Single<Status>

    fun uploadMedia(uri: Uri): Single<Media>
}