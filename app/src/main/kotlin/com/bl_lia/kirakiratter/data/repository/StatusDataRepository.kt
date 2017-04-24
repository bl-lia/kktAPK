package com.bl_lia.kirakiratter.data.repository

import android.net.Uri
import com.bl_lia.kirakiratter.data.repository.datasource.status.StatusDataStoreFactory
import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.repository.StatusRepository
import com.bl_lia.kirakiratter.domain.value_object.Media
import com.bl_lia.kirakiratter.domain.value_object.StatusForm
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StatusDataRepository
    @Inject constructor(
            private val statusDataStoreFactory: StatusDataStoreFactory
    ): StatusRepository {

    override fun post(text: String, warning: String?, sensitive: Boolean?, mediaId: String?): Single<Status> =
            statusDataStoreFactory.create().post(text, warning, sensitive, mediaId)

    override fun post(statusForm: StatusForm): Single<Status> =
            statusDataStoreFactory.create().post(statusForm)

    override fun uploadMedia(uri: Uri): Single<Media> =
            statusDataStoreFactory.create().uploadMedia(uri)
}