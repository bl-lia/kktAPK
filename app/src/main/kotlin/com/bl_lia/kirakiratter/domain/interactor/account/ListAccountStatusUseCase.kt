package com.bl_lia.kirakiratter.domain.interactor.account

import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.repository.AccountRepository
import io.reactivex.Single

class ListAccountStatusUseCase(
        private val accountRepository: AccountRepository,
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Status>>(threadExecutor, postExecutionThread) {

    override fun build(params: Array<out Any>): Single<List<Status>> =
            accountRepository.statuses(params[0] as Int)
}