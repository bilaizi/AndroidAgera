package com.example.bilaizi.androidagera.step2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.bilaizi.androidagera.R;
import com.google.android.agera.MutableRepository;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Updatable;

public class Step2ActivityFinal extends AppCompatActivity {

    private static final String VALUE_KEY = "VALUE_KEY";
    private final MutableRepository<Integer> valueRepository = Repositories.mutableRepository(0);
    private Repository<String> textValueRepository;
    private Updatable mTextValueUpdatable;
    private TextView mValueTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step2);
        mValueTv = (TextView) findViewById(R.id.step2_value_tv);
        Button incrementBt = (Button) findViewById(R.id.increment_bt);
        incrementBt.setOnClickListener(view -> valueRepository.accept(valueRepository.get() + 1));
        textValueRepository = Repositories.repositoryWithInitialValue(getString(R.string.not_available))
                        .observe(valueRepository)
                        .onUpdatesPerLoop()
                        .getFrom(valueRepository)
                        .thenTransform(Object::toString)
                        .compile();
        mTextValueUpdatable = () -> mValueTv.setText(textValueRepository.get());
    }

    @Override
    protected void onStart() {
        super.onStart();
        textValueRepository.addUpdatable(mTextValueUpdatable);
    }

    @Override
    protected void onStop() {
        textValueRepository.removeUpdatable(mTextValueUpdatable);
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(VALUE_KEY, valueRepository.get());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(VALUE_KEY)) {
            valueRepository.accept(savedInstanceState.getInt(VALUE_KEY));
        }
    }
}
