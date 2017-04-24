package com.bl_lia.kirakiratter.domain.interactor.auth

import io.reactivex.Single
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.repository.AuthRepository
import com.bl_lia.kirakiratter.domain.value_object.AppCredentials

class AuthenticateAppUseCase(
        private val authRepository: AuthRepository,
        private val appName: String,
        private val oauthRedirectUri: String,
        private val oauthScopes: String,
        private val appWebsite: String,
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
) : SingleUseCase<AppCredentials>(threadExecutor, postExecutionThread) {

    override fun build(params: Array<out Any>): Single<AppCredentials> {
        return authRepository.authenticateApp(appName, oauthRedirectUri, oauthScopes, appWebsite)
    }
}