package rezept_day.ucoz.ru.notes;

import android.provider.BaseColumns;

public class NotesContract {//Класс в котором будут храниться все данные о нашей базе данных ( название БД, заголовки столбцов и т.д.
    //Содержит столько классов - сколько у нас будет таблиц. В наем примере одна таблица
    public static final class NotesEntry implements BaseColumns{
        public static final String TABLE_NAME = "notes";//Название нашей таблицы
        public static final String COLUMN_TITLE = "title";//Первая колонка - заголовок заметки
        public static final String COLUMN_DESCRIPTION = "description";//Вторая колонка - описание заметки
        public static final String COLUMN_DAY_OF_WEEK = "day_of_week";//Третья колонка - день недели
        public static final String COLUMN_PRIORITY = "priority";//Четвертая - приоритет

        public static final String TYPE_TEXT = "TEXT";//Тип данных хранимых в таблице
        public static final String TYPE_INTEGER = "INTEGER";//Тип данных хранимых в таблице

        public static final String CREATE_COMMAND = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" + _ID + " " + TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE +
                " " + TYPE_TEXT + ", " + COLUMN_DESCRIPTION + " " + TYPE_TEXT + ", " +
                COLUMN_DAY_OF_WEEK + " " + TYPE_TEXT + ", " + COLUMN_PRIORITY + " " +
                TYPE_INTEGER + ")";//Строка содержащая команду создания таблицы

        public static final String DROP_COMMAND = "DROP TABLE IF EXISTS " + TABLE_NAME;//Команда для удаления таблицы

    }
}
