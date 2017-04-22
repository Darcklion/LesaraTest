package com.lesaratest

import android.app.Application
import com.facebook.stetho.Stetho
import com.lesaratest.presenter.ProductManager

class App: Application() {

    companion object {
        var productsManager = ProductManager()
    }

    override fun onCreate() {
        super.onCreate()

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build())
    }
}