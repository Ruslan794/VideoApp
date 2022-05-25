package com.example.data.di

import org.koin.core.component.KoinScopeComponent
import org.koin.core.scope.Scope

class RepositoryScope : KoinScopeComponent {
    override val scope: Scope
        get() = getKoin().createScope<RepositoryScope>()
}