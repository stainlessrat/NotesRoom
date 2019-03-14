package rezept_day.ucoz.ru.notes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class NotesViewHolder extends RecyclerView.ViewHolder{//Этот класс будет содержать все видимые части нашей заметки

    private TextView textViewTitle;
    private TextView textViewDescription;
    private TextView textViewDayOfWeek;
    private TextView textViewPriority;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.textViewTitle);
        textViewDescription = itemView.findViewById(R.id.textViewDescription);
        textViewDayOfWeek = itemView.findViewById(R.id.textViewDayOfWeek);
        textViewPriority = itemView.findViewById(R.id.textViewPriority);
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

    public TextView getTextViewPriority() {
        return textViewPriority;
    }
}
