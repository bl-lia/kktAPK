package com.bl_lia.kirakiratter.data.repository.datasource.account

import com.bl_lia.kirakiratter.data.cache.AccountCache
import com.bl_lia.kirakiratter.domain.entity.Account
import com.bl_lia.kirakiratter.domain.entity.Relationship
import com.bl_lia.kirakiratter.domain.entity.Status
import io.reactivex.Single

class MemoryAccountDataStore(
        private val accountCache: AccountCache
) : AccountDataStore {

    override fun status(id: Int): Single<List<Status>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moreStatus(id: Int, maxId: Int?, sinceId: Int?): Single<List<Status>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun relationship(id: Int): Single<Relationship> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun follow(id: Int): Single<Relationship> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unfollow(id: Int): Single<Relationship> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun verifyCredentials(): Single<Account> = Single.just(accountCache.credentials)
}