package com.bl_lia.kirakiratter.domain.value_object

data class AccessToken(
        val origin: String = "api",
        val accessToken: String
)