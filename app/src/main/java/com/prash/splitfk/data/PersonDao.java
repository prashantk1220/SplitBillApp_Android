package com.prash.splitfk.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Person person);

    @Query("DELETE FROM person_table")
    void deleteAll();

    @Query("SELECT * FROM person_table order by name ASC")
    LiveData<List<Person>> getAllpersons();

    @Query(("SELECT trans from person_table where name=:personName"))
    double getTransaction(String personName);
}
