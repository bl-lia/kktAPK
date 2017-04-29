package com.bl_lia.kirakiratter.presentation.presenter

import com.bl_lia.kirakiratter.domain.entity.Notification
import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

@PerFragment
class NotificationPresenter
    @Inject constructor(
            @Named("listNotification")
            private val listNotification: SingleUseCase<List<Notification>>,
            @Named("listMoreNotification")
            private val listMoreNotification: SingleUseCase<List<Notification>>,
            @Named("reblogStatus")
            private val reblogStatus: SingleUseCase<Status>,
            @Named("unreblogStatus")
            private val unreblogStatus: SingleUseCase<Status>
    ) : Presenter{

    override fun resume() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun destroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun fetchNotification(): Single<List<Notification>> =
            listNotification.execute()

    fun fetchMoreNotification(maxId: Int): Single<List<Notification>>? {
        if (listMoreNotification.processing) return null

        return listMoreNotification.execute(maxId)
    }

    fun reblog(status: Status): Single<Status> {
        val target = status.reblog ?: status
        if (target.reblogged) {
            return unreblogStatus.execute(status.id)
        } else {
            return reblogStatus.execute(status.id)
        }
    }
}