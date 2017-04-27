package com.bl_lia.kirakiratter.presentation.service

import com.bl_lia.kirakiratter.domain.entity.Notification
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

@PerFragment
class FetchNotificationJobServiceExtra
    @Inject constructor(
            @Named("getNotification")
            private val getNotification: SingleUseCase<Notification>
    ){

    fun getNotification(id: Int): Single<Notification> = getNotification.execute(id)
}