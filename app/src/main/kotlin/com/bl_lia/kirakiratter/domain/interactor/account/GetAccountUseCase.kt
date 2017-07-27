package com.bl_lia.kirakiratter.domain.interactor.account

import com.bl_lia.kirakiratter.domain.entity.Account
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.repository.AccountRepository
import io.reactivex.Single

class GetAccountUseCase(
        val accountRepository: AccountRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
): SingleUseCase<Account>(threadExecutor, postExecutionThread) {

    override fun build(params: Array<out Any>): Single<Account> = accountRepository.account(params[0] as Int)
}