package com.example.myapp;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface WorkOutDao {
    @Query("SELECT * FROM workouts WHERE user =:name")
    LiveData<List<WorkOuts>> getAll(String name);
    @Query("SELECT * FROM workouts WHERE user =:name AND kind =:kind AND mainExercise =:main AND date BETWEEN :date1 AND :date2")
    LiveData<List<WorkOuts>> getSpecifics(String name,String kind,String main,long date1,long date2);

    @Insert
    void Insert(WorkOuts workOuts);

    @Delete
    void delete(WorkOuts workOuts);

    @Update
    void update(WorkOuts workOuts);

    @Query("DELETE FROM workouts")
    void deleteAll();
}
