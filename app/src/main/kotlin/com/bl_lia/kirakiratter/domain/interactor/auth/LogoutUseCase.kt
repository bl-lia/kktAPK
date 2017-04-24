package com.bl_lia.kirakiratter.domain.interactor.auth

import io.reactivex.Completable
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.CompletableUseCase
import com.bl_lia.kirakiratter.domain.repository.AuthRepository

class LogoutUseCase(
        private val authRepository: AuthRepository,
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
) : CompletableUseCase<Void>(threadExecutor, postExecutionThread) {

    override fun build(params: Array<out Any>): Completable {
        return authRepository.logout()
    }
}