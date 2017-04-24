package com.bl_lia.kirakiratter.domain.interactor.auth

import io.reactivex.Single
import com.bl_lia.kirakiratter.domain.entity.AuthInfo
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.repository.AuthRepository

class GetAuthInfoUseCase(
        private val authRepository: AuthRepository,
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
) : SingleUseCase<AuthInfo>(threadExecutor, postExecutionThread) {

    override fun build(params: Array<out Any>): Single<AuthInfo> {
        return authRepository.authInfo()
    }
}