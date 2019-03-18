package rezept_day.ucoz.ru.notes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNotes;
    private final ArrayList<Note> notes = new ArrayList<>();
    private NotesAdapter adapter;
    private NotesDBHelper dbHelper;//создаем объект класса помошника работы с БД
    private SQLiteDatabase database;//создаем объект класса SQLLiteDatabase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);

        //работа с БД(присваиваем значение объекту)
        dbHelper = new NotesDBHelper(this);
        //Создание БД
        database = dbHelper.getWritableDatabase();
        //database.delete(NotesContract.NotesEntry.TABLE_NAME,null,null);//команда для удаления данных из БД
//        if (notes.isEmpty()) {
//            notes.add(new Note("Парикмахер", "Сделать прическу", "Понедельник", 2));
//            notes.add(new Note("Баскетбол", "Игра со школьной командой", "Вторник", 3));
//            notes.add(new Note("Магазин", "Купить новые джинсы", "Понедельник", 3));
//            notes.add(new Note("Стоматогол", "Выличить зубы", "Понедельник", 2));
//            notes.add(new Note("Парикмахер", "Сделать прическу к выпускному", "Среда", 1));
//            notes.add(new Note("Баскетбол", "Игра со школьной командой", "Вторник", 3));
//            notes.add(new Note("Магазин", "Купить новые джинсы", "Понедельник", 3));
//        }
//
//        //Запишим данные в базу из массива
//        for(Note note: notes){
//            //Для того чтобы вставить данные нам нужен объект класса ContentValues
//            ContentValues contentValues = new ContentValues();
//            //и сюда мы можем класть данные парами: ключ-значение
//            contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, note.getTitle());
//            contentValues.put(NotesContract.NotesEntry.COLUMN_DESCRIPTION, note.getDescription());
//            contentValues.put(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK, note.getDayOfWeek());
//            contentValues.put(NotesContract.NotesEntry.COLUMN_PRIORITY, note.getPriority());
//            database.insert(NotesContract.NotesEntry.TABLE_NAME,null, contentValues);//Вставляем наши данные в нашу БД
//        }

        getData();//получаем данные из БД и присваиваем нашему массиву

        //adapter = new NotesAdapter(notesFromDB);//Для проверки БД
        adapter = new NotesAdapter(notes);
        //Осталось только указать нашему RecyclerView как отображать список ( по горизонтали, по вертикали, сеткой)
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));//По вертикали
        //recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));//По горизонтали
        //recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));//По горизонтали, распологаются в обратном направлении
        //recyclerViewNotes.setLayoutManager(new GridLayoutManager(this, 3));//Сеткой, передаем контекст и количество столбцов

        //Установить у RecyclerView Adapter
        recyclerViewNotes.setAdapter(adapter);
        adapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                Toast.makeText(MainActivity.this, "Номер позиции: " + position, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLongClick(int position) {
                remove(position);
            }
        });

        //Для смахивания элементов(удаление Свайпом)
        //Второй пераметр - куда смахивать, в примере - влево или вправо
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                //Обработка смахивания, i - направление куда смахивается элемент
                remove(viewHolder.getAdapterPosition());
            }
        });
        //Метод создан, теперь нам надо применить его к RecyclerView
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);
    }

    private void remove(int position){
        //удаление записи из БД
        int id = notes.get(position).getId();
        //Строка которую надо удалить
        String where = NotesContract.NotesEntry._ID + " = ?";
        String[] whereArgs = new String[]{Integer.toString(id)};
        database.delete(NotesContract.NotesEntry.TABLE_NAME, where, whereArgs);

        getData();//получаем данные из БД и присваиваем нашему массиву

        //notes.remove(position);//Удаляем позицию в списке, на которую нажал пользователь
        adapter.notifyDataSetChanged();//Говорим адаптеру: "обрати внимание: данные изменились"
    }

    public void onClickAddNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    //Метод для получения данных из БД и присваивание их массиву
    private void getData(){
        notes.clear();//Очистим наш массив
        //Для проверки работы БД. Будем считывать данные и сохранять в новый ArrayList
        //ArrayList<Note> notesFromDB = new ArrayList<>();
        //для этого используется объект класса Cursor
        //В cursor храняться все (columns:null - получить все записи) записи из БД

        //напримермы хотим выводить только важные события, для этого в cursor мы использем поля selectio  и selectionArgs, таким же образом как и в методе remove() при удалении данных
        String selection = NotesContract.NotesEntry.COLUMN_PRIORITY + " < ?";//хотим выводить данные меньше чем аргумент, который подставим вместо знака вопрос
        String[] selectionArgs = new String[]{"2"};//Например 2

        Cursor cursor = database.query(NotesContract.NotesEntry.TABLE_NAME,null,null,null,null,null, NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK);
        //Cursor cursor = database.query(NotesContract.NotesEntry.TABLE_NAME,null,selection,selectionArgs,null,null, NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK);//ротиер использования выборки
        //orderBy - сортировка по заданному параметру
        //Чтобы получить одну строку вызвается метод moveToNext
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry._ID));//Получаем ID
            String title = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE));//Получаем заголовок
            String description = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DESCRIPTION));//Получаем описание
            int dayOfWeek = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK));//Получаем день недели
            int priority = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_PRIORITY));//Получаем приоритет
            Note note = new Note(id, title, description, dayOfWeek, priority);
            //notesFromDB.add(note);
            notes.add(note);
        }
        cursor.close();//Необходимо закрывать cursor после выполнения с ним всех действий
    }
}
