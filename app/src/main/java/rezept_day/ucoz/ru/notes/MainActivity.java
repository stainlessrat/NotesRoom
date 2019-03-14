package rezept_day.ucoz.ru.notes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNotes;
    private ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);

        notes.add(new Note("Парикмахер", "Сделать прическу", "Понедельник", 2));
        notes.add(new Note("Баскетбол", "Игра со школьной командой", "Вторник", 3));
        notes.add(new Note("Магазин", "Купить новые джинсы", "Понедельник", 3));
        notes.add(new Note("Стоматогол", "Выличить зубы", "Понедельник", 2));
        notes.add(new Note("Парикмахер", "Сделать прическу к выпускному", "Среда", 1));
        notes.add(new Note("Баскетбол", "Игра со школьной командой", "Вторник", 3));
        notes.add(new Note("Магазин", "Купить новые джинсы", "Понедельник", 3));

        NotesAdapter adapter = new NotesAdapter(notes);
        //Осталось только указать нашему RecyclerView как отображать список ( по горизонтали, по вертикали, сеткой)
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));//По вертикали
        //recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));//По горизонтали
        //recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));//По горизонтали, распологаются в обратном направлении
        //recyclerViewNotes.setLayoutManager(new GridLayoutManager(this, 3));//Сеткой, передаем контекст и количество столбцов

        //Установить у RecyclerView Adapter
        recyclerViewNotes.setAdapter(adapter);
    }
}
