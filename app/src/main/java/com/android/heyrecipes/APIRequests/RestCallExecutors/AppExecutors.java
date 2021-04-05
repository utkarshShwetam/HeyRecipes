package com.android.heyrecipes.APIRequests.RestCallExecutors;

import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import android.os.Handler;

public class AppExecutors {
    private static AppExecutors instance;

    public AppExecutors() {
    }

    public static AppExecutors getInstance() {
        if (instance == null)
            instance = new AppExecutors();
        return instance;
    }

    private final Executor DiskIO = Executors.newSingleThreadExecutor();

    private final Executor mainThreadExecutor = new MainThreadExecutor();

    public Executor DiskIO() {
        return DiskIO;
    }

    public Executor MainThread() {
        return mainThreadExecutor;
    }

    private static class MainThreadExecutor implements Executor {

        private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }

    }
}

