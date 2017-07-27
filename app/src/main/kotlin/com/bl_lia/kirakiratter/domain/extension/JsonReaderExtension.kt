package com.bl_lia.kirakiratter.domain.extension

import com.bl_lia.kirakiratter.BuildConfig
import com.bl_lia.kirakiratter.domain.entity.Account
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken

fun JsonReader.nextStringExtra(): String? =
        if (peek() == JsonToken.NULL) {
            null
        } else {
            nextString()
        }

fun JsonReader.nextBooleanExtra(default: Boolean): Boolean =
        if (peek() == JsonToken.NULL) {
            default
        } else {
            nextBoolean()
        }

fun JsonReader.nextIntExtra(): Int? =
        if (peek() == JsonToken.NULL) {
            null
        } else {
            nextInt()
        }


fun JsonReader.readAccount(): Account? {
    if (peek() == JsonToken.NULL) {
        nextNull()
        return null
    }

    var id: Int? = null
    var userName: String? = null
    var displayName: String? = null
    var avatar: String? = null
    var header: String? = null
    var note: String? = null
    var followersCount: Int? = null
    var followingCount: Int? = null
    var statusesCount: Int? = null

    beginObject()
    while (hasNext()) {
        if (peek() == JsonToken.NULL) {
            nextNull()
            continue
        }

        val nextName = nextName()
        when (nextName) {
            "id" -> id = nextInt()
            "username" -> userName = nextStringExtra()
            "display_name" -> displayName = nextStringExtra()
            "avatar" -> {
                val p = nextStringExtra()
                val path =
                        if (p != null && p.startsWith("https://")) {
                            p
                        } else {
                            BuildConfig.API_URL + "/" + p
                        }
                avatar = path
            }
            "header" -> header = nextStringExtra()
            "note" -> note = nextStringExtra()
            "followers_count" -> followersCount = nextIntExtra()
            "following_count" -> followingCount = nextIntExtra()
            "statuses_count" -> statusesCount = nextIntExtra()
            else -> skipValue()
        }

    }
    endObject()

    return Account(
            id = id!!,
            userName = userName,
            displayName = displayName,
            avatar = avatar,
            header = header,
            note = note,
            followersCount = followersCount,
            followingCount = followingCount,
            statusesCount = statusesCount
    )
}


