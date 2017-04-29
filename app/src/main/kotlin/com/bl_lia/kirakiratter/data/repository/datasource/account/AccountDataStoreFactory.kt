package com.bl_lia.kirakiratter.data.repository.datasource.account

import com.bl_lia.kirakiratter.data.cache.AccountCache
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountDataStoreFactory
    @Inject constructor(
            private val retrofit: Retrofit,
            private val accountCache: AccountCache
    ){

    fun create(): AccountDataStore = createApi()

    fun createApi(): ApiAccountDataStore {
        val service = retrofit.create(AccountService::class.java)
        return ApiAccountDataStore(service, accountCache)
    }

    fun createCache(): MemoryAccountDataStore {
        return MemoryAccountDataStore(accountCache)
    }
}