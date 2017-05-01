package com.bl_lia.kirakiratter.presentation.presenter

import com.bl_lia.kirakiratter.domain.entity.Account
import com.bl_lia.kirakiratter.domain.entity.Relationship
import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.presentation.internal.di.PerActivity
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

@PerActivity
class AccountPresenter
    @Inject constructor(
            @Named("listAccountStatus")
            private val listAccountStatus: SingleUseCase<List<Status>>,
            @Named("listMoreAccountStatus")
            private val listMoreAccountStatus: SingleUseCase<List<Status>>,
            @Named("getRelationship")
            private val getRelationship: SingleUseCase<Relationship>,
            @Named("follow")
            private val follow: SingleUseCase<Relationship>,
            @Named("unfollow")
            private val unfollow: SingleUseCase<Relationship>,
            @Named("verifyCredentials")
            private val verifyCredentials: SingleUseCase<Account>
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

    fun relationship(account: Account): Single<Relationship> = getRelationship.execute(account.id)

    fun follow(account: Account): Single<Relationship> = follow.execute(account.id)

    fun unfollow(account: Account): Single<Relationship> = unfollow.execute(account.id)

    fun verifyCredentials(): Single<Account> = verifyCredentials.execute()
}