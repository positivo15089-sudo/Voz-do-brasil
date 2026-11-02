package br.com.vozdobrasil.studio

import android.app.Application
import br.com.vozdobrasil.studio.data.local.AppDatabase

class VozDoBrasilApp : Application() {
    val database: AppDatabase by lazy { AppDatabase.getInstance(this) }
}
