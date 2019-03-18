package rezept_day.ucoz.ru.notes;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {
    private static NotesDatabase database;
    private static final String DB_NAME = "notes2.db";
    private static final Object LOCK = new Object();//Объект для синхронизации запросов к БД

    //Singleton - шаблон проектирования
    public static NotesDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {//Если БД нет, то создать ее
                //Создание БД
                database = Room.databaseBuilder(context, NotesDatabase.class, DB_NAME)
                        .allowMainThreadQueries()//Только для тестов. Никогда так не делайте в реальном приложении. Позволяет обращаться к БД из главного потока
                        .build();//
            }
        }
        return database;
    }

    public abstract NotesDao notesDao();
}
