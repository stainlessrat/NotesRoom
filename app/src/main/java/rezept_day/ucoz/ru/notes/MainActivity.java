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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNotes;
    private final ArrayList<Note> notes = new ArrayList<>();
    private NotesAdapter adapter;

    //БД
    private NotesDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //получить БД
        database = NotesDatabase.getInstance(this);

        //программно убрать ActionBar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);

        //adapter = new NotesAdapter(notesFromDB);//Для проверки БД
        adapter = new NotesAdapter(notes);
        //Осталось только указать нашему RecyclerView как отображать список ( по горизонтали, по вертикали, сеткой)
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));//По вертикали
        //recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));//По горизонтали
        //recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));//По горизонтали, распологаются в обратном направлении
        //recyclerViewNotes.setLayoutManager(new GridLayoutManager(this, 3));//Сеткой, передаем контекст и количество столбцов

        getData();//Получаем данные из БД

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
        Note note = notes.get(position);//Получаем нашу записку
        database.notesDao().deleteNote(note);//Удаляем записку из БД
        getData();//получаем данные

        //notes.remove(position);//Удаляем позицию в списке, на которую нажал пользователь
        adapter.notifyDataSetChanged();//Говорим адаптеру: "обрати внимание: данные изменились"
    }

    public void onClickAddNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    private void getData(){
        List<Note> notesFromDB = database.notesDao().getAllNotes();//получаем в ArrayList данные из БД
        notes.clear();//Очищаем список
        notes.addAll(notesFromDB);//Добавляем в ArrayList для вывода
    }
}
