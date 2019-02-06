package com.prash.splitfk;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static volatile ViewModelFactory INSTANCE;

    private final Application mApplication;

    public static ViewModelFactory getInstance(Application application) {

        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application);
                }
            }
        }
        return INSTANCE;
    }

    ViewModelFactory(Application application){
        mApplication = application;
    }

    public static void destroy(){
        INSTANCE = null;
    }

    public <T extends ViewModel>T create(Class<T> modelClass){
        if(modelClass.isAssignableFrom(PersonViewModel.class))
            return (T) PersonViewModel.getInstance(mApplication);

        throw new IllegalArgumentException("No such class found: "+modelClass.getName());
    }

}
