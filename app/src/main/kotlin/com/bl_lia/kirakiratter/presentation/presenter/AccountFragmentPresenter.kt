package com.bl_lia.kirakiratter.presentation.presenter

import com.bl_lia.kirakiratter.domain.entity.Account
import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
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
            private val listMoreAccountStatus: SingleUseCase<List<Status>>
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
}