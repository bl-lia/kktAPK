package com.bl_lia.kirakiratter.domain.interactor.push_notification

import com.bl_lia.kirakiratter.BuildConfig
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.repository.AuthRepository
import com.bl_lia.kirakiratter.domain.repository.PushNotificationRepository
import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.Single

class UnregisterTokenUseCase(
        private val authRepository: AuthRepository,
        private val pushNotificationRepository: PushNotificationRepository,
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
) : SingleUseCase<String>(threadExecutor, postExecutionThread) {

    override fun build(params: Array<out Any>): Single<String> {
        val pushApiUrl  = params[0] as String
        val instanceUrl = BuildConfig.API_URL
        val accessToken = authRepository.cachedAccessToken().blockingGet().accessToken
        val deviceToken = FirebaseInstanceId.getInstance().getToken()!!

        return pushNotificationRepository.unregister(pushApiUrl, instanceUrl, accessToken, deviceToken)
    }
}