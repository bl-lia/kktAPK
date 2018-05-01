package com.bl_lia.kirakiratter.data.type_adapter

import com.bl_lia.kirakiratter.domain.entity.Account
import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.extension.nextBooleanExtra
import com.bl_lia.kirakiratter.domain.extension.nextStringExtra
import com.bl_lia.kirakiratter.domain.extension.readAccount
import com.bl_lia.kirakiratter.domain.value_object.Content
import com.bl_lia.kirakiratter.domain.value_object.Media
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import org.joda.time.DateTime
import java.util.*

class StatusTypeAdapter : TypeAdapter<Status>() {

    override fun write(out: JsonWriter?, value: Status?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun read(input: JsonReader?): Status? {

        var id: Int? = null
        var spoilerText: String? = null
        var content: String? = null
        var account: Account? = null
        var reblog: Status? = null
        var reblogged: Boolean = false
        var favourited: Boolean = false
        var mediaAttachments: List<Media>? = null
        var sensitive: Boolean = false
        var visibility: String? = null
        var createdAt: Date? = null
        var url: String? = null

        if (input == null || input.peek() == JsonToken.NULL) {
            input?.nextNull()
            return null
        }

        input.beginObject()
        while (input.hasNext()) {
            if (input.peek() == JsonToken.NULL) {
                input.nextNull()
                continue
            }

            val nextName = input.nextName()
            when (nextName) {
                "id" -> id = input.nextInt()
                "spoiler_text" -> spoilerText = input.nextStringExtra()
                "content" -> content = input.nextStringExtra()
                "account" -> account = input.readAccount()
                "reblog" -> reblog = input.reblog()
                "favourited" -> favourited = input.nextBooleanExtra(false)
                "reblogged" -> reblogged = input.nextBooleanExtra(false)
                "media_attachments" -> mediaAttachments = input.mediaList()
                "sensitive" -> sensitive = input.nextBooleanExtra(false)
                "visibility" -> visibility = input.nextStringExtra()
                "created_at" -> createdAt = DateTime.parse(input.nextString()).toDate()
                "url" -> url = input.nextStringExtra()
                else -> input.skipValue()
            }
        }
        input.endObject()

        val c = Content(header = spoilerText, body = content)

        return Status(
                id = id!!,
                content = c,
                account = account,
                reblog = reblog,
                reblogged = reblogged,
                favourited = favourited,
                mediaAttachments = mediaAttachments ?: listOf(),
                sensitive = sensitive,
                visibility = visibility,
                createdAt = createdAt,
                url = url)
    }


    private fun JsonReader.reblog(): Status? {
        if (peek() == JsonToken.NULL) {
            nextNull()
            return null
        }

        var id: Int? = null
        var spoilerText: String? = null
        var content: String? = null
        var account: Account? = null
        var favourited: Boolean = false
        var reblogged: Boolean = false
        var mediaAttachments: List<Media>? = null
        var sensitive: Boolean = false
        var visibility: String? = null
        var createdAt: Date? = null
        var url: String? = null

        beginObject()
        while (hasNext()) {
            if (peek() == JsonToken.NULL) {
                nextNull()
                continue
            }

            val nextName = nextName()
            when (nextName) {
                "id" -> id = nextInt()
                "spoiler_text" -> spoilerText = nextStringExtra()
                "content" -> content = nextStringExtra()
                "account" -> account = readAccount()
                "favourited" -> favourited = nextBooleanExtra(false)
                "reblogged" -> reblogged = nextBooleanExtra(false)
                "media_attachments" -> mediaAttachments = mediaList()
                "sensitive" -> sensitive = nextBooleanExtra(false)
                "visibility" -> visibility = nextStringExtra()
                "created_at" -> createdAt = DateTime.parse(nextStringExtra()).toDate()
                "url" -> url = nextStringExtra()
                else -> skipValue()
            }
        }
        endObject()

        val c = Content(header = spoilerText, body = content)

        return Status(
                id = id!!,
                content = c,
                account = account,
                reblogged = reblogged,
                favourited = favourited,
                mediaAttachments = mediaAttachments ?: listOf(),
                sensitive = sensitive,
                visibility = visibility,
                createdAt = createdAt,
                url = url)
    }

    private fun JsonReader.mediaList(): List<Media>? {
        if (peek() == JsonToken.NULL) {
            nextNull()
            return null
        }

        val list: MutableList<Media> = mutableListOf()

        beginArray()
        while (hasNext()) {
            if (peek() == JsonToken.BEGIN_OBJECT) {
                media()?.let {
                    list.add(it)
                }
            }
        }
        endArray()

        return list.toList()
    }

    private fun JsonReader.media(): Media? {
        if (peek() == JsonToken.NULL) {
            nextNull()
            return null
        }

        var id: Int? = null
        var type: String? = null
        var previewUrl: String? = null
        var url: String? = null
        var textUrl: String? = null

        beginObject()
        while (hasNext()) {
            if (peek() == JsonToken.NULL) {
                nextNull()
                continue
            }

            val nextName = nextName()
            when (nextName) {
                "id" -> id = nextInt()
                "type" -> type = nextStringExtra()
                "preview_url" -> previewUrl = nextStringExtra()
                "url" -> url = nextStringExtra()
                "text_url" -> textUrl = nextStringExtra()
                else -> skipValue()
            }
        }
        endObject()

        return Media(
                id = id!!,
                type = type!!,
                previewUrl = previewUrl,
                url = url,
                textUrl = textUrl
        )
    }

}