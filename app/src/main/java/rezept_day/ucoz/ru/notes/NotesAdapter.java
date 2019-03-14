package rezept_day.ucoz.ru.notes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder> {

    private ArrayList<Note> notes;//массив для хранения заметок

    public NotesAdapter(ArrayList<Note> notes) {
        this.notes = notes;//получаем массив к конструкторе
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Берем наш макет и передаем его в ViewHolder
        //Для этого получаем наш layout.note_item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_item, viewGroup, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder notesViewHolder, int i) {
        //Метод для получения нашей заметки из массива
        Note note = notes.get(i);
        //Установка полей заметки в макет item
        notesViewHolder.getTextViewTitle().setText(note.getTitle());
        notesViewHolder.getTextViewDescription().setText(note.getDescription());
        notesViewHolder.getTextViewDayOfWeek().setText(note.getDayOfWeek());
        int colorID;
        int priority = note.getPriority();
        switch (priority){
            case 1:
                colorID = notesViewHolder.itemView.getResources().getColor(android.R.color.holo_red_light);
                break;
            case 2:
                colorID = notesViewHolder.itemView.getResources().getColor(android.R.color.holo_orange_light);
                break;
            default:
                colorID = notesViewHolder.itemView.getResources().getColor(android.R.color.holo_green_light);
                break;
        }
        notesViewHolder.getTextViewTitle().setBackgroundColor(colorID);
    }

    @Override
    public int getItemCount() {
        //Просто возвращвет количество элементов в массиве
        return notes.size();
    }
}
