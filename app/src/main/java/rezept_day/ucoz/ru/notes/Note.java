package rezept_day.ucoz.ru.notes;


import android.arch.persistence.room.Entity;

//Класс который будет содержать всю информацию о заметке
//Для того чтобы объект хранить в базе данных надо пометить его анатацией @Entity
@Entity
public class Note {
    private int id;//ID заметки из БД
    private String title;//Заголовок заметки
    private String description;//Описание заметки
    private int dayOfWeek;//День недели
    private int priority;//Приоритет выполнения

    public Note(int id, String title, String description, int dayOfWeek, int priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public int getPriority() {
        return priority;
    }

    //метод который будет преобразовывать числовой день недели в строку
    public static String getDayasString(int position){
        switch (position){
            case 1:
                return "Понедельник";
            case 2:
                return "Вторник";
            case 3:
                return "Среда";
            case 4:
                return "Четверг";
            case 5:
                return "Пятница";
            case 6:
                return "Суббота";
            default:
                return "Воскресенье";
        }
    }
}
