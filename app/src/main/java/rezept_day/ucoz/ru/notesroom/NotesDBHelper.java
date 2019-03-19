package rezept_day.ucoz.ru.notesroom;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesDBHelper extends SQLiteOpenHelper {
    //Класс для работы с базой данных

    private static final String DB_NAME = "notes.db";//Название нашей базы данных
    private static final int DB_VERSION = 2;//Версия БД, если надобудет обновить, добавляем +1 и т.д.

    public NotesDBHelper(Context context) {
        //Конструктор
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Вызывается при создании таблицы в базе данных. В качестве параметра принимает базу данных
        db.execSQL(NotesContract.NotesEntry.CREATE_COMMAND);//Делаем запрос к БД и передаем нашу команду на создание БД
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Вызывается при обновлении версии БД
        db.execSQL(NotesContract.NotesEntry.DROP_COMMAND);//Удаляем старую БД
        onCreate(db);//И создаем новую
    }
}
