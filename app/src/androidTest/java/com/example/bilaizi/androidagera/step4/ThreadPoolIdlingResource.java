package com.example.bilaizi.androidagera.step4;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.ThreadPoolExecutor;

final class ThreadPoolIdlingResource implements IdlingResource {

    @NonNull
    private final ThreadPoolExecutor mThreadPoolExecutor;

    @NonNull
    private final String mName;

    @Nullable
    private ResourceCallback mCallback;

    private ThreadPoolIdlingResource(@NonNull final ThreadPoolExecutor executor,
                                     @NonNull final String name) {
        mThreadPoolExecutor = executor;
        mName = name;
    }

    public static IdlingResource newThreadPoolIdlingResource(
            @NonNull final ThreadPoolExecutor executor, @NonNull final String name) {
        return new ThreadPoolIdlingResource(executor, name);
    }

    @Override
    public String getName() {
        return "ThreadPool: " + mName;
    }

    @Override
    public synchronized boolean isIdleNow() {
        if (mThreadPoolExecutor.getQueue().isEmpty() && mThreadPoolExecutor.getActiveCount() <= 0) {
            if (mCallback != null) {
                mCallback.onTransitionToIdle();
            }
            return true;
        }
        return false;
    }

    @Override
    public void registerIdleTransitionCallback(@Nullable final ResourceCallback resourceCallback) {
        mCallback = resourceCallback;
    }
}