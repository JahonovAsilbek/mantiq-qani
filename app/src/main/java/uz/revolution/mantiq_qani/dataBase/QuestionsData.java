package uz.revolution.mantiq_qani.dataBase;

import android.content.Context;
import android.database.Cursor;
import uz.revolution.mantiq_qani.libs.DataBaseHelper;
import uz.revolution.mantiq_qani.model.MyModel;
import java.util.ArrayList;

public class QuestionsData extends DataBaseHelper {

    private static QuestionsData dataBase;
    private final String TABLE_NAME = "KubokKubkov";
    private final String ID = "_id";
    private final String QUESTION = "question";
    private final String ANSWER = "answer";
    private final String COMMENT = "comment";
    private final String SOURCE = "source";
    private final String AUTHOR = "author";

    private QuestionsData(Context context) {
        super(context, "exampleData.db");
    }

    public static void init(Context context) {
        if (dataBase == null) {
            dataBase = new QuestionsData(context);
        }
    }

    public static QuestionsData getDatabase() {
        return dataBase;
    }

    public ArrayList<MyModel> getData(int level) {
        mDataBase = getWritableDatabase();
        mDataBase = getReadableDatabase();
        ArrayList<MyModel> data = new ArrayList<>();
        Cursor cursor = mDataBase.rawQuery("select * from " + TABLE_NAME +" LIMIT "+(level+5), null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            data.add(new MyModel(
                    cursor.getInt(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(QUESTION)),
                    cursor.getString(cursor.getColumnIndex(ANSWER)),
                    cursor.getString(cursor.getColumnIndex(COMMENT)),
                    cursor.getString(cursor.getColumnIndex(SOURCE)),
                    cursor.getString(cursor.getColumnIndex(AUTHOR))
            ));
            cursor.moveToNext();
        }
        cursor.close();
        return data;
    }
}
