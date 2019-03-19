package rezept_day.ucoz.ru.notesroom;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

public class MainViewModel extends AndroidViewModel {//Класс для работы с БД в отдельном потоке

    private static NotesDatabase database;//Получаем нашу БД

    private LiveData<List<Note>> notes;//Получаем объект, который будет хранить все наши заметки

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = NotesDatabase.getInstance(getApplication());
        notes = database.notesDao().getAllNotes();
    }

    public LiveData<List<Note>> getNotes() {//геттер на все заметки
        return notes;
    }

    //метод вставляющий заметку в БД
    public void insertNote(Note note){
        new InsertTask().execute(note);
    }

    private static class InsertTask extends AsyncTask<Note, Void, Void>{

        @Override
        protected Void doInBackground(Note... notes) {
            if(notes != null && notes.length > 0){
                database.notesDao().insertNote(notes[0]);
            }
            return null;
        }
    }

    //метод удаляющий заметку из БД
    public void deleteNote(Note note){
        new DeleteTask().execute(note);
    }

    private static class DeleteTask extends AsyncTask<Note, Void, Void>{

        @Override
        protected Void doInBackground(Note... notes) {
            if(notes != null && notes.length > 0){
                database.notesDao().deleteNote(notes[0]);
            }
            return null;
        }
    }

    //метод удаляющий все заметки из БД
    public void deleteAllNotes(){
        new DeleteAllTask().execute();
    }

    private static class DeleteAllTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... notes) {

            database.notesDao().deleteAllNotes();
            return null;
        }
    }
}
