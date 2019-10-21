package io.gripxtech.odoo

import androidx.multidex.MultiDexApplication
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import com.odoo.common.utils.CookiePrefs
import com.odoo.common.utils.LetterTileProvider
import com.odoo.common.utils.Retrofit2Helper
import io.gripxtech.odoo.di.DaggerAppInjector
import timber.log.Timber
import javax.inject.Inject

class App : MultiDexApplication(), HasAndroidInjector {

    /** An `AndroidInjector` used to inject dependencies into Activities. */
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    private val letterTileProvider: LetterTileProvider by lazy {
        LetterTileProvider(this)
    }


    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Retrofit2Helper.app = this
        CookiePrefs.app = this

//        ObjectBox.init(this)

        DaggerAppInjector.builder()
            .application(app = this)
            .build()
            .apply {
                inject(app = this@App)
            }
    }

    fun getLetterTile(displayName: String): ByteArray =
        letterTileProvider.getLetterTile(displayName)
}