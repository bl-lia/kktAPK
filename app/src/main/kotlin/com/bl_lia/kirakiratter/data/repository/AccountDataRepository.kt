package com.bl_lia.kirakiratter.data.repository

import com.bl_lia.kirakiratter.data.repository.datasource.account.AccountDataStoreFactory
import com.bl_lia.kirakiratter.domain.entity.Account
import com.bl_lia.kirakiratter.domain.entity.Relationship
import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.repository.AccountRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountDataRepository
    @Inject constructor(
            private val accountDataStoreFactory: AccountDataStoreFactory
    ): AccountRepository {

    override fun statuses(id: Int): Single<List<Status>> = accountDataStoreFactory.create().status(id)
    override fun moreStatuses(id: Int, maxId: Int?, sinceId: Int?): Single<List<Status>> = accountDataStoreFactory.create().moreStatus(id, maxId, sinceId)
    override fun relationship(id: Int): Single<Relationship> = accountDataStoreFactory.create().relationship(id)

    override fun follow(id: Int): Single<Relationship> = accountDataStoreFactory.create().follow(id)
    override fun unfollow(id: Int): Single<Relationship> = accountDataStoreFactory.create().unfollow(id)

    override fun verifyCredentials(): Single<Account> =
            accountDataStoreFactory.createCache()
                    .verifyCredentials()
                    .flatMap { account ->
                        if (account.isInvalid) {
                            accountDataStoreFactory.createApi().verifyCredentials()
                        } else {
                            Single.just(account)
                        }
                    }
}