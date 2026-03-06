package com.example.myapp;
import androidx.annotation.NonNull;
import androidx.room.*;
@Entity
public class WorkOuts {

    @Override
    public String toString(){
        return "Workout{" +
                " id = " + id +
                " user = " + user +
                " date = " + date +
                " hour = " + hour +
               " length = " + length  +
               " kind = " + kind +
               " main Exercise = " + mainExercise +
                '\'' + '}';

    }
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "user", defaultValue = "sdd")
    public String user;

    @ColumnInfo(name = "date", defaultValue = "sxdsd")
    public long date;

    @ColumnInfo(name = "hour", defaultValue = "sds")
    public String hour;

    @ColumnInfo(name = "length", defaultValue = "5")
    public int length;

    @ColumnInfo(name = "kind",defaultValue = "jjd")
    public String kind;

    @ColumnInfo(name = "mainExercise",defaultValue = "ddd")
    public String mainExercise;


@Ignore
    public WorkOuts(@NonNull String user, long date, String hour, String kind, int length, String mainExercise) {
        this.user = user;
        this.date = date;
        this.hour = hour;
        this.kind = kind;
        this.length = length;
        this.mainExercise = mainExercise;
    }

    public WorkOuts(@NonNull String user,long date, String mainExercise, String kind) {
        this.date = date;
        this.user = user;
        this.mainExercise = mainExercise;
        this.kind = kind;
    }

    @NonNull
    public String getUser() {
        return user;
    }

    public long getDate() {
        return date;
    }

    public String getHour() {
        return hour;
    }

    public int getLength() {
        return length;
    }

    public String getKind() {
        return kind;
    }

    public String getMainExercise() {
        return mainExercise;
    }
}
