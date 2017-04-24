package com.bl_lia.kirakiratter.domain.interactor.status

import android.net.Uri
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.repository.StatusRepository
import com.bl_lia.kirakiratter.domain.value_object.Media
import io.reactivex.Single

class UploadMediaUseCase(
        private val statusRepository: StatusRepository,
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
) : SingleUseCase<Media>(threadExecutor, postExecutionThread) {

    override fun build(params: Array<out Any>): Single<Media> {
        return statusRepository.uploadMedia(params[0] as Uri)
    }
}