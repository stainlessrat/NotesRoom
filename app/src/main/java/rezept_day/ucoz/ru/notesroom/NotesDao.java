package rezept_day.ucoz.ru.notesroom;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NotesDao {
    //DAO - database access object - объект доступа к данным
    //В ней мы создадим методы для доступа к БД
    @Query("SELECT * FROM notes ORDER BY dayOfWeek")//с параметром: выбрать(SELECT) все (*) из (FROM) таблицы с именем notes (notes), отсортировав (ORDER BY) по параметру dayOfWeek (dayOfWeek)
                                                     //если добавить ASC (dayOfWeek ASC")) - отсортируется по возрастанию
                                                    //если добавить DESC - в обратном порядке, по убыванию
    LiveData<List<Note>> getAllNotes();//так как он будет вызываться при обращении к базе его необходимо пометить анатацией @Query
                                        //будет возвращать объект LiveData, который содержит список записей (List<Note>)

    @Insert
    void insertNote(Note note);//Метод для добавления записки

    @Delete
    void deleteNote(Note note);//Метод для удаления записи

    @Query("DELETE FROM notes")
    void deleteAllNotes();//Очистить таблицу
}
