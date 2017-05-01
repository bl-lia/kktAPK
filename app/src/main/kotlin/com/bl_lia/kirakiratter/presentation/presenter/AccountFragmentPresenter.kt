package com.bl_lia.kirakiratter.presentation.presenter

import com.bl_lia.kirakiratter.domain.entity.Account
import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.value_object.Translation
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

@PerFragment
class AccountFragmentPresenter
    @Inject constructor(
            @Named("listAccountStatus")
            private val listAccountStatus: SingleUseCase<List<Status>>,
            @Named("listMoreAccountStatus")
            private val listMoreAccountStatus: SingleUseCase<List<Status>>,
            @Named("favouriteStatus")
            private val favouriteStatus: SingleUseCase<Status>,
            @Named("unfavouriteStatus")
            private val unfavouriteStatus: SingleUseCase<Status>,
            @Named("reblogStatus")
            private val reblogStatus: SingleUseCase<Status>,
            @Named("unreblogStatus")
            private val unreblogStatus: SingleUseCase<Status>,
            @Named("translateContent")
            private val translateContent: SingleUseCase<List<Translation>>
    ): Presenter {

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

    fun fetchStatus(account: Account): Single<List<Status>> = listAccountStatus.execute(account.id)

    fun fetchMoreStatus(account: Account, maxId: Int): Single<List<Status>>? {
        if (listMoreAccountStatus.processing) return null

        return listMoreAccountStatus.execute(account.id, maxId)
    }

    fun reblog(status: Status): Single<Status> {
        val target = status.reblog ?: status
        if (target.reblogged) {
            return unreblogStatus.execute(status.id)
        } else {
            return reblogStatus.execute(status.id)
        }
    }

    fun favourite(status: Status): Single<Status> {
        val target = status.reblog ?: status
        if (target.favourited) {
            return unfavouriteStatus.execute(status.id.toString())
        } else {
            return favouriteStatus.execute(status.id.toString())
        }
    }
}