package com.bl_lia.kirakiratter.domain.interactor.config

import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.repository.ConfigRepository
import io.reactivex.Single

class GetSimpleModeUseCase(
        private val configRepository: ConfigRepository,
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
) : SingleUseCase<Boolean>(threadExecutor, postExecutionThread) {

    override fun build(params: Array<out Any>): Single<Boolean> =
            configRepository.simpleMode()
}