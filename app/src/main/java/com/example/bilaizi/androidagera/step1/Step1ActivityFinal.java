package com.example.bilaizi.androidagera.step1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.bilaizi.androidagera.R;
import com.google.android.agera.MutableRepository;
import com.google.android.agera.Repositories;
import com.google.android.agera.Updatable;

public class Step1ActivityFinal extends AppCompatActivity {

    private MutableRepository<String> mStringRepo;
    private Updatable mLoggerUpdatable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step1);

        // Create a MutableRepository
        mStringRepo = Repositories.mutableRepository("Initial value");

        // Create an Updatable
        mLoggerUpdatable = () -> Log.d("AGERA", mStringRepo.get());

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Connect the dots:
        mStringRepo.addUpdatable(mLoggerUpdatable);

        // Change the repository's value
        mStringRepo.accept("Hello Agera!");
    }

    @Override
    protected void onStop() {
        mStringRepo.removeUpdatable(mLoggerUpdatable);
        super.onStop();
    }
}
