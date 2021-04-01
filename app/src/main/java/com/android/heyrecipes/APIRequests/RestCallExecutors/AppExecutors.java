package com.android.heyrecipes.APIRequests.RestCallExecutors;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {
    private static AppExecutors instance;

    public AppExecutors() {
    }

    public static AppExecutors getInstance(){
        if(instance==null)
            instance=new AppExecutors();
        return instance;
    }

    private final ScheduledExecutorService networkIO= Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService newtorkIO(){
        return networkIO;
    }
}

