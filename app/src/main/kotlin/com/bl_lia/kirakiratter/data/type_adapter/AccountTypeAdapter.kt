package com.bl_lia.kirakiratter.data.type_adapter

import com.bl_lia.kirakiratter.domain.entity.Account
import com.bl_lia.kirakiratter.domain.extension.readAccount
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class AccountTypeAdapter : TypeAdapter<Account>() {

    override fun write(out: JsonWriter?, value: Account?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun read(input: JsonReader?): Account? {
        return input?.readAccount()
    }

}