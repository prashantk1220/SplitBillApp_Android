package com.prash.splitfk.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
import java.util.Map;

public class PersonRepository {
    private static PersonRepository mPersonRepository;
    private PersonDao mPersonDao;
    private LiveData<List<Person>> mAllPersons;
    private static double mPersonTransaction;

    public PersonRepository(Application application){
        PersonRoomDatabase db = PersonRoomDatabase.getDatabase(application);
        mPersonDao = db.PersonDao();
        mAllPersons = mPersonDao.getAllpersons();
    }

    public static PersonRepository getInstance(Application application){
        if(mPersonRepository == null)
            return new PersonRepository(application);
        else
            return mPersonRepository;
    }

    public LiveData<List<Person>> getAllPersons(){
        return mAllPersons;
    }

    public double getTransaction(String name) {
        new fetchAsyncTask(mPersonDao).execute(name);
        return mPersonTransaction;
    }

    private static class fetchAsyncTask extends AsyncTask<String, Void, Double> {
        private PersonDao mAsyncPersonDao;
        fetchAsyncTask(PersonDao dao) {mAsyncPersonDao = dao;}
        @Override
        protected Double doInBackground(String... params) {
            return mAsyncPersonDao.getTransaction(params[0]);
        }

        protected void onPostExecute(Double trans){
            mPersonTransaction = trans;
        }

    }

    public void insert(Person Person){
        new insertAsyncTask(mPersonDao).execute(Person);
    }

    private static class insertAsyncTask extends AsyncTask<Person, Void, Void> {

        private PersonDao mAsyncPersonDao;

        insertAsyncTask(PersonDao dao){
            mAsyncPersonDao = dao;
        }

        @Override
        protected Void doInBackground(Person... Persons) {
            mAsyncPersonDao.insert(Persons[0]);
            return null;
        }
    }
}
