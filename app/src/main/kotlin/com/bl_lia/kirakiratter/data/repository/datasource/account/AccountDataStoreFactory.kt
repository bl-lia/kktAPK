package com.bl_lia.kirakiratter.data.repository.datasource.account

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountDataStoreFactory
    @Inject constructor(
            private val retrofit: Retrofit
    ){

    fun create(): AccountDataStore {
        val service = retrofit.create(AccountService::class.java)
        return ApiAccountDataStore(service)
    }
}