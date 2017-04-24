package com.bl_lia.kirakiratter.domain.interactor.auth

import io.reactivex.Single
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.repository.AuthRepository
import com.bl_lia.kirakiratter.domain.value_object.AccessToken

class FetchOauthTokenUseCase(
        private val authRepository: AuthRepository,
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
) : SingleUseCase<AccessToken>(threadExecutor, postExecutionThread) {

    override fun build(params: Array<out Any>): Single<AccessToken> {
        return authRepository.accessToken(params[0].toString())
    }
}