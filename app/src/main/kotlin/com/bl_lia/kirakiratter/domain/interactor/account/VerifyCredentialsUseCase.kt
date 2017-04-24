package com.bl_lia.kirakiratter.domain.interactor.account

import com.bl_lia.kirakiratter.domain.entity.Account
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.repository.AccountRepository
import io.reactivex.Single

class VerifyCredentialsUseCase(
        private val accountRepository: AccountRepository,
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
) : SingleUseCase<Account>(threadExecutor, postExecutionThread) {

    override fun build(params: Array<out Any>): Single<Account> = accountRepository.verifyCredentials()
}