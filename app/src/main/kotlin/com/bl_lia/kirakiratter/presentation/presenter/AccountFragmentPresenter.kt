package com.bl_lia.kirakiratter.presentation.presenter

import android.support.v4.app.Fragment
import com.bl_lia.kirakiratter.domain.entity.Account
import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.extension.containsJapanese
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.value_object.Translation
import com.bl_lia.kirakiratter.presentation.fragment.AccountFragment
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

@PerFragment
class AccountFragmentPresenter
    @Inject constructor(
            private val fragment: Fragment,
            @Named("listAccountStatus")
            private val listAccountStatus: SingleUseCase<List<Status>>,
            @Named("listMoreAccountStatus")
            private val listMoreAccountStatus: SingleUseCase<List<Status>>,
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

    fun fetchStatus(account: Account): Single<List<Status>> = listAccountStatus.execute(account.id)

    fun fetchMoreStatus(account: Account, maxId: Int): Single<List<Status>>? {
        if (listMoreAccountStatus.processing) return null

        return listMoreAccountStatus.execute(account.id, maxId)
    }

    fun reblog(status: Status): Single<Status> {
        val target = status.reblog ?: status
        if (target.reblogged) {
            return unreblogStatus.execute(status.id)
        } else {
            return reblogStatus.execute(status.id)
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

    fun translation(status: Status) {
        val target = status.reblog ?: status

        if (target.content?.body != null) {
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
                                        (fragment as AccountFragment).tranlateText(status, list, error)
                                    }
                        } else {
                            (fragment as AccountFragment).tranlateText(status, listOf(), Exception("Error"))
                        }
                    }
        } else {
            (fragment as AccountFragment).tranlateText(status, listOf(), Exception("Error"))
        }
    }
}