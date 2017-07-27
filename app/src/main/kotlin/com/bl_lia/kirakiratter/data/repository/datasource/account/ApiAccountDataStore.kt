package com.bl_lia.kirakiratter.data.repository.datasource.account

import com.bl_lia.kirakiratter.data.cache.AccountCache
import com.bl_lia.kirakiratter.domain.entity.Account
import com.bl_lia.kirakiratter.domain.entity.Relationship
import com.bl_lia.kirakiratter.domain.entity.Status
import io.reactivex.Single

class ApiAccountDataStore(
        private val accountService: AccountService,
        private val accountCache: AccountCache
) : AccountDataStore {

    override fun status(id: Int): Single<List<Status>> = accountService.status(id)

    override fun moreStatus(id: Int, maxId: Int?, sinceId: Int?): Single<List<Status>> = accountService.status(id, maxId, sinceId)

    override fun relationship(id: Int): Single<Relationship> =
            accountService.relationships(id)
                    .flatMap { Single.just(it.first()) }

    override fun follow(id: Int): Single<Relationship> = accountService.follow(id)

    override fun unfollow(id: Int): Single<Relationship> = accountService.unfollow(id)

    override fun verifyCredentials(): Single<Account> =
            accountService.verifyCredentials()
                    .doAfterSuccess { account ->
                        accountCache.credentials = account
                    }

    override fun account(id: Int): Single<Account> = accountService.account(id)
}