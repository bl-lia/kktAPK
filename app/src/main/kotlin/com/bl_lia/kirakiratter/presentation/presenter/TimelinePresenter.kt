package com.bl_lia.kirakiratter.presentation.presenter

import android.support.v4.app.Fragment
import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.value_object.Translation
import com.bl_lia.kirakiratter.presentation.fragment.TimelineFragment
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

@PerFragment
class TimelinePresenter
    @Inject constructor(
            private val fragment: Fragment,
            @Named("getHomeTimeline")
            private val getHomeTimeline: SingleUseCase<List<Status>>,
            @Named("getMoreHomeTimeline")
            private val getMoreHomeTimeline: SingleUseCase<List<Status>>,
            @Named("getPublicTimeline")
            private val getPublicTimeline: SingleUseCase<List<Status>>,
            @Named("getMorePublicTimeline")
            private val getMorePublicTimeline: SingleUseCase<List<Status>>,
            @Named("favouriteStatus")
            private val favouriteStatus: SingleUseCase<Status>,
            @Named("unfavouriteStatus")
            private val unfavouriteStatus: SingleUseCase<Status>,
            @Named("reblogStatus")
            private val reblogStatus: SingleUseCase<Status>,
            @Named("unreblogStatus")
            private val unreblogStatus: SingleUseCase<Status>,
            @Named("translateContent")
            private val translateContent: SingleUseCase<List<Translation>>
    ): Presenter {

    override fun resume() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun destroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun fetchTimeline(scope: TimelineFragment.Scope): Single<List<Status>> =
        when (scope) {
            TimelineFragment.Scope.Home -> {
                getHomeTimeline.execute()
            }
            TimelineFragment.Scope.Local -> {
                getPublicTimeline.execute()
            }
        }

    fun fetchMoreTimeline(scope: TimelineFragment.Scope, maxId: String): Single<List<Status>>? {
        if (getMoreHomeTimeline.processing) return null

        when (scope) {
            TimelineFragment.Scope.Home -> {
                return getMoreHomeTimeline.execute(maxId)
            }
            TimelineFragment.Scope.Local -> {
                return getMorePublicTimeline.execute(maxId)
            }
        }
    }

    fun favourite(status: Status): Single<Status> {
        val target = status.reblog ?: status
        if (target.favourited) {
            return unfavouriteStatus.execute(status.id.toString())
        } else {
            return favouriteStatus.execute(status.id.toString())
        }
    }

    fun reblog(status: Status): Single<Status> {
        val target = status.reblog ?: status
        if (target.reblogged) {
            return unreblogStatus.execute(status.id)
        } else {
            return reblogStatus.execute(status.id)
        }
    }

    fun translation(status: Status) {
        val target = status.reblog ?: status

        if (target.content.body != null) {
            val remoteConfig = FirebaseRemoteConfig.getInstance()
            remoteConfig.fetch()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            remoteConfig.activateFetched()
                            val key = remoteConfig.getString("translation_api_key")
                            val text = target.content.body.toString()
                            val sourceLang = if (text.containsJapanese()) "ja" else "en"
                            val targetLang = if (sourceLang == "ja") "en" else "ja"
                            translateContent.execute(key, sourceLang, targetLang, text)
                                    .subscribe { list, error ->
                                        (fragment as TimelineFragment).tranlateText(status, list, error)
                                    }
                        } else {
                            (fragment as TimelineFragment).tranlateText(status, listOf(), Exception("Error"))
                        }
                    }
        } else {
            (fragment as TimelineFragment).tranlateText(status, listOf(), Exception("Error"))
        }
    }

    private fun String.containsJapanese(): Boolean {
        val japanese = this.toCharArray().filter { char ->
            when (Character.UnicodeBlock.of(char)) {
                Character.UnicodeBlock.HIRAGANA,
                Character.UnicodeBlock.KATAKANA,
                Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS,
                Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS,
                Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION -> true
                else -> false
            }
        }

        return japanese.count() > 0
    }
}