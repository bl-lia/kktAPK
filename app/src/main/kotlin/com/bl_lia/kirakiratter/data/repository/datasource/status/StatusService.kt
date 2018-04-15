package com.bl_lia.kirakiratter.data.repository.datasource.status

import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.value_object.Media
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface StatusService {

    @FormUrlEncoded
    @POST("api/v1/statuses")
    fun post(
            @Field("status")
            text: String,
            @Field("spoiler_text")
            warning: String? = null,
            @Field("in_reply_to_id")
            inReplyToId: Int? = null,
            @Field("sensitive")
            sensitive: Boolean? = null,
            @Field("visibility")
            visibility: String? = null,
            @Field("media_ids[]")
            mediaIds: List<String>? = null
    ): Single<Status>

    @Multipart
    @POST("api/v1/media")
    fun uploadMedia(@Part file: MultipartBody.Part): Single<Media>
}