package com.bl_lia.kirakiratter.domain.value_object

data class AppCredentials(
        val origin: String = "api",
        val clientId: String,
        val clientSecret: String
)