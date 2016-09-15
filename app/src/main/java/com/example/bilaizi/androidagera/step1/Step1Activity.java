package com.example.bilaizi.androidagera.step1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.bilaizi.androidagera.R;
import com.google.android.agera.Observable;
import com.google.android.agera.Receiver;
import com.google.android.agera.Supplier;
import com.google.android.agera.Updatable;

import java.util.ArrayList;
import java.util.List;


public class Step1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step1);
        // Create a Supplier-Observable-Receiver
        MyDataSupplier myDataSupplier = new MyDataSupplier();
        // Create an Updatable
        Updatable updatable = () -> Log.d("AGERA", myDataSupplier.get());
        // Connect the dots:
        myDataSupplier.addUpdatable(updatable);
        myDataSupplier.accept("Hello Agera!");
    }

    private static class MyDataSupplier implements Observable,
            Supplier<String>, Receiver<String> {
        List<Updatable> mUpdatables = new ArrayList<>();
        private String mValue;

        @Override
        public void addUpdatable(@NonNull Updatable updatable) {
            mUpdatables.add(updatable);
        }

        @Override
        public void removeUpdatable(@NonNull Updatable updatable) {
            mUpdatables.remove(updatable);
        }

        @Override
        public void accept(@NonNull String value) {
            mValue = value;
            // Notify the updatables that we have new data
            for (Updatable updatable : mUpdatables) {
                updatable.update();
            }
        }

        @NonNull
        @Override
        public String get() {
            return mValue;
        }
    }
}
