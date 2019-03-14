package rezept_day.ucoz.ru.notes;


//Класс который будет содержать всю информацию о заметке
public class Note {
    private String title;//Заголовок заметки
    private String description;//Описание заметки
    private String dayOfWeek;//День недели
    private int priority;//Приоритет выполнения

    public Note(String title, String description, String dayOfWeek, int priority) {
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public int getPriority() {
        return priority;
    }
}
