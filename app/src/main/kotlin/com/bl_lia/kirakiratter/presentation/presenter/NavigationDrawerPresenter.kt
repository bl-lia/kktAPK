package com.bl_lia.kirakiratter.presentation.presenter

import io.reactivex.Completable
import com.bl_lia.kirakiratter.domain.interactor.CompletableUseCase
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import javax.inject.Inject
import javax.inject.Named

@PerFragment
class NavigationDrawerPresenter
    @Inject constructor(
            @Named("logout")
            private val logout: CompletableUseCase<Void>
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
}