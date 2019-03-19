package rezept_day.ucoz.ru.notesroom;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rezept_day.ucoz.ru.notes.R;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<Note> notes;//массив для хранения
    private OnNoteClickListener onNoteClickListener;

    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    public NotesAdapter(List<Note> notes) {
        this.notes = notes;//получаем массив в конструкторе
    }

    public interface OnNoteClickListener {
        void onNoteClick(int position);
        void onLongClick(int position);
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
        notesViewHolder.getTextViewDayOfWeek().setText(Note.getDayasString(note.getDayOfWeek() + 1));
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

    public class NotesViewHolder extends RecyclerView.ViewHolder{//Этот класс будет содержать все видимые части нашей заметки

        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewDayOfWeek;


        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewDayOfWeek = itemView.findViewById(R.id.textViewDayOfWeek);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onNoteClickListener != null){
                        onNoteClickListener.onNoteClick(getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {//обрабатывает долгое нажатие на элементе
                    if(onNoteClickListener != null){
                        onNoteClickListener.onLongClick(getAdapterPosition());
                    }
                    return true;
                }
            });
        }

        public TextView getTextViewTitle() {
            return textViewTitle;
        }

        public TextView getTextViewDescription() {
            return textViewDescription;
        }

        public TextView getTextViewDayOfWeek() {
            return textViewDayOfWeek;
        }
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public List<Note> getNotes() {
        return notes;
    }
}
