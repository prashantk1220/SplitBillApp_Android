package com.prash.splitfk;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.prash.splitfk.data.Person;
import com.prash.splitfk.data.PersonRepository;

import java.util.List;


public class PersonViewModel extends AndroidViewModel {

    private static PersonViewModel mPersonViewModel;
    private PersonRepository mRepository;
    private LiveData<List<Person>> mPersonList;

    public PersonViewModel(@NonNull Application application){
        super(application);
        mRepository = PersonRepository.getInstance(application);
        mPersonList = mRepository.getAllPersons();
    }

    public static PersonViewModel getInstance(@NonNull Application application){
        if(mPersonViewModel != null)
            return mPersonViewModel;
        else
            return new PersonViewModel(application);

    }

    public double getTransaction(String personName){
        return mRepository.getTransaction(personName);
    }

    LiveData<List<Person>> getPersonList(){
        return mPersonList;
    }

    public void insertPerson(Person Person){
        mRepository.insert(Person);
    }
}
