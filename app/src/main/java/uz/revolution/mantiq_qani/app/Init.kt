package uz.revolution.mantiq_qani.app

import android.app.Application
import uz.revolution.mantiq_qani.dataBase.MySharedPreferences
import uz.revolution.mantiq_qani.dataBase.QuestionsData

class Init : Application() {
    override fun onCreate() {
        super.onCreate()
        MySharedPreferences.init(this)
        QuestionsData.init(this)
    }

    override fun onTerminate() {
        QuestionsData.getDatabase().close()
        super.onTerminate()
    }
}