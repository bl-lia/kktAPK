package com.bl_lia.kirakiratter.presentation.presenter

import android.net.Uri
import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.value_object.Media
import com.bl_lia.kirakiratter.domain.value_object.StatusForm
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

@PerFragment
class KatsuPresenter
    @Inject constructor(
            @Named("postStatus")
            private val postStatus: SingleUseCase<Status>,
            @Named("uploadMedia")
            private val uploadMedia: SingleUseCase<Media>
    ) : Presenter {

    override fun resume() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun destroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun post(text: String, warning: String? = null, inReplyToId: Int? = null, sensitive: Boolean = false, attachment: Uri? = null): Single<Status> {

        if (attachment != null) {
            return uploadMedia.execute(attachment)
                    .flatMap { media ->
                        postStatus.execute(StatusForm(
                                status = text,
                                spoilerText = warning,
                                inReplyToId = inReplyToId,
                                mediaId = media.id
                        ))
                    }
        } else {
            return postStatus.execute(StatusForm(
                    status = text,
                    spoilerText = warning,
                    inReplyToId = inReplyToId
            ))
        }
    }
}