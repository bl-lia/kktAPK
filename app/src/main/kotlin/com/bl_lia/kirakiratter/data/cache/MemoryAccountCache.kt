package com.bl_lia.kirakiratter.data.cache

import com.bl_lia.kirakiratter.domain.entity.Account
import javax.inject.Inject

class MemoryAccountCache
    @Inject constructor(): AccountCache {

    override var credentials: Account = Account.invalidAccount()
}