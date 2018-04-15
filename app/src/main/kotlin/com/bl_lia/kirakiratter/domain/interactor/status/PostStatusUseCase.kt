package com.bl_lia.kirakiratter.domain.interactor.status

import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.repository.StatusRepository
import com.bl_lia.kirakiratter.domain.value_object.StatusForm
import io.reactivex.Single

class PostStatusUseCase(
        private val statusRepository: StatusRepository,
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
) : SingleUseCase<Status>(threadExecutor, postExecutionThread) {

    override fun build(params: Array<out Any>): Single<Status> =
            if (params[0] is StatusForm) {
                postForm(params)
            } else {
                postParam(params)
            }

    private fun postForm(params: Array<out Any>): Single<Status> {
        val form = params[0] as StatusForm
        return statusRepository.post(form)
    }

    private fun postParam(params: Array<out Any>): Single<Status> {
        val text = params[0] as String
        val warning = params[1] as String
        val inReplyToId = params[2] as Int
        val sensitive = params[3] as Boolean
        val visibility = params[4] as String
        if (params.size > 5) {
            val mediaId: Int? = params[5] as Int?
            val mediaIdString: String? =
                    if (mediaId != null) {
                        mediaId.toString()
                    } else {
                        null
                    }
            return statusRepository.post(text, warning, inReplyToId, sensitive, visibility, mediaIdString)
        } else {
            return statusRepository.post(text, warning, inReplyToId, sensitive, visibility)
        }
    }
}