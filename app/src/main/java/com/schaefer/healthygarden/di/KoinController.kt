package com.schaefer.healthygarden.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

object KoinController {
    private val loadedModules: HashSet<Module> = hashSetOf()

    @JvmStatic
    fun start(
        application: Application,
        modules: List<Module>
    ) {
        startKoin {
            androidContext(application)
        }

        loadModules(modules)
    }

    fun loadModules(modules: List<Module>) {
        modules.filterNot { loadedModules.contains(it) }
            .takeIf { it.isNotEmpty() }
            ?.apply {
                loadKoinModules(this)
                loadedModules.addAll(this)
            }
    }

    fun unloadModules(modules: List<Module>) {
        modules.filter { loadedModules.contains(it) }
            .takeIf { it.isNotEmpty() }
            ?.apply {
                unloadKoinModules(this)
                loadedModules.removeAll(this)
            }
    }
}