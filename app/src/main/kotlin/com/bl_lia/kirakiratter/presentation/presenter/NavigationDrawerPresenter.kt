package com.bl_lia.kirakiratter.presentation.presenter

import com.bl_lia.kirakiratter.domain.interactor.CompletableUseCase
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

@PerFragment
class NavigationDrawerPresenter
    @Inject constructor(
            @Named("logout")
            private val logout: CompletableUseCase<Void>,
            @Named("registerToken")
            private val registerToken: SingleUseCase<String>,
            @Named("unregisterToken")
            private val unregisterToken: SingleUseCase<String>,
            @Named("isRegisteredToken")
            private val isRegisteredToken: SingleUseCase<Boolean>,
            @Named("setSimpleMode")
            private val setSimpleMode: SingleUseCase<Boolean>,
            @Named("getSimpleMode")
            private val getSimpleMode: SingleUseCase<Boolean>
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

    fun logout(): Completable {
        return logout.execute()
    }

    fun registerToken(pushApiUrl: String): Single<String> {
        return registerToken.execute(pushApiUrl)
    }

    fun unregisterToken(pushApiUrl: String): Single<String> = unregisterToken.execute(pushApiUrl)

    fun isRegisteredToken(pushApiUrl: String): Single<Boolean> = isRegisteredToken.execute(pushApiUrl)

    fun setSimpleMode(enabled: Boolean): Single<Boolean> = setSimpleMode.execute(enabled)

    fun getSimpleMode(): Single<Boolean> = getSimpleMode.execute()
}