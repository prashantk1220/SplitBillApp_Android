package com.prash.splitfk.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Person.class}, version = 1, exportSchema = false)
public abstract class PersonRoomDatabase extends RoomDatabase {

    public abstract PersonDao PersonDao();

    private static volatile PersonRoomDatabase INSTANCE;

    static PersonRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (PersonRoomDatabase.class){
                if(INSTANCE == null)
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PersonRoomDatabase.class, "person_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final PersonDao mDao;

        PopulateDbAsync(PersonRoomDatabase db){
            mDao = db.PersonDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
//            mDao.deleteAll();
//            Person person = new Person();
//            person.setName("No transactions yet");
//            mDao.insert(person);
            return null;
        }
    }

}
