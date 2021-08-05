package uz.revolution.mantiq_qani.app;

import android.app.Application;

import uz.revolution.mantiq_qani.dataBase.Database;
import uz.revolution.mantiq_qani.dataBase.QuestionsData;

public class Init extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Database.init(this);
        QuestionsData.init(this);
    }

    @Override
    public void onTerminate() {
        Database.getDatabase();
        QuestionsData.getDatabase().close();
        super.onTerminate();
    }
}
