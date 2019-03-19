package rezept_day.ucoz.ru.notesroom;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

//Класс который будет содержать всю информацию о заметке. То что здесь есть называется объект POJO (Plain Old Java Object)
//Для того чтобы объект хранить в базе данных надо пометить его анатацией @Entity
@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;//ID заметки из БД, помечаем его что это и есть ID, указваем что генерируется автоматически

    private String title;//Заголовок заметки
    private String description;//Описание заметки
    private int dayOfWeek;//День недели
    private int priority;//Приоритет выполнения

    //ОБЯЗАТЕЛЬНО конструктор, геттеры и сеттеры
    public Note(int id, String title, String description, int dayOfWeek, int priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
    }

    //так как мы будем сами создавать заметки, нам нужен конструктор без ID
    //но так как он будет мешать нашей БД, помечаем его анатацией @Ignore
    @Ignore
    public Note(String title, String description, int dayOfWeek, int priority) {
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

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setPriority(int priority) {
        this.priority = priority;
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
