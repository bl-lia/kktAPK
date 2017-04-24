package com.bl_lia.kirakiratter.domain.interactor.translation

import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.repository.TranslationRepository
import com.bl_lia.kirakiratter.domain.value_object.Translation
import io.reactivex.Single

class TranslateContentUseCase(
        private val translationRepository: TranslationRepository,
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Translation>>(threadExecutor, postExecutionThread) {

    override fun build(params: Array<out Any>): Single<List<Translation>> {
        val key = params[0] as String
        val sourceLang = params[1] as String
        val targetLang = params[2] as String
        val query = params[3] as String

        return translationRepository.translate(key, sourceLang, targetLang, query)
    }
}