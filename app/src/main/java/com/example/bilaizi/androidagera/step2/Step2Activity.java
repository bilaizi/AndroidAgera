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

public class Step2Activity extends AppCompatActivity {

    public static final String VALUE_KEY = "VALUE_KEY";
    private final MutableRepository<Integer> valueRepository = Repositories.mutableRepository(0);
    private Repository<String> textValueRepository;
    private Updatable mTextValueUpdatable;
    private TextView mValueTv;
    private Button mIncrementBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step2);
        mValueTv = (TextView) findViewById(R.id.step2_value_tv);
        mIncrementBt = (Button) findViewById(R.id.increment_bt);
        // Set onClickListener
        mIncrementBt.setOnClickListener(view -> valueRepository.accept(valueRepository.get() + 1));
        // Create complex repository:
        textValueRepository = Repositories.repositoryWithInitialValue("N/A")
                .observe(valueRepository)
                .onUpdatesPerLoop()
                .getFrom(valueRepository)
                .thenTransform(input -> String.format("%d", input))
                .compile();
        // Create updatable
        mTextValueUpdatable = () -> mValueTv.setText(textValueRepository.get());
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Add updatables here.
        textValueRepository.addUpdatable(mTextValueUpdatable);
    }

    @Override
    protected void onStop() {
        // Remove updatables here.
        textValueRepository.removeUpdatable(mTextValueUpdatable);
        super.onStop();
    }
}
