package com.bl_lia.kirakiratter.domain.interactor.auth

import io.reactivex.Completable
import com.bl_lia.kirakiratter.domain.entity.AuthInfo
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.CompletableUseCase
import com.bl_lia.kirakiratter.domain.repository.AuthRepository

class ResetAuthInfoUseCase(
        private val authRepository: AuthRepository,
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
) : CompletableUseCase<AuthInfo>(threadExecutor, postExecutionThread) {

    override fun build(params: Array<out Any>): Completable {
        return authRepository.reset(params[0] as AuthInfo)
    }
}