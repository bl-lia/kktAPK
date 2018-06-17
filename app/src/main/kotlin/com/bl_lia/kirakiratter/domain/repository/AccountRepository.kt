package com.bl_lia.kirakiratter.domain.repository

import com.bl_lia.kirakiratter.domain.entity.Account
import com.bl_lia.kirakiratter.domain.entity.Relationship
import com.bl_lia.kirakiratter.domain.entity.Status
import io.reactivex.Single

interface AccountRepository {

    fun statuses(id: Int): Single<List<Status>>
    fun moreStatuses(id: Int, maxId: String? = null, sinceId: Int? = null): Single<List<Status>>
    fun relationship(id: Int): Single<Relationship>

    fun follow(id: Int): Single<Relationship>
    fun unfollow(id: Int): Single<Relationship>

    fun verifyCredentials(): Single<Account>

    fun account(id: Int): Single<Account>
}