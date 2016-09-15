package com.example.bilaizi.androidagera.step3;

import android.support.v4.util.Pair;

import com.example.bilaizi.androidagera.CalculatorOperations;
import com.example.bilaizi.androidagera.R;
import com.google.android.agera.Result;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CalculatorOperationsTest {

    private static final Pair<Integer, Integer> OPERANDS_1_2 = new Pair<>(1, 2);
    private static final Pair<Integer, Integer> OPERANDS_1_0 = new Pair<>(1, 0);

    private static final Result<Integer> OPERATION_ADD = Result.present(R.id.radioButtonAdd);
    private static final Result<Integer> OPERATION_SUB = Result.present(R.id.radioButtonSub);
    private static final Result<Integer> OPERATION_MULT = Result.present(R.id.radioButtonMult);
    private static final Result<Integer> OPERATION_DIV = Result.present(R.id.radioButtonDiv);

    @Test
    public void attemptOperationAdd() throws Exception {
        Pair<Integer, Integer> operands = new Pair<>(1, 2);
        Result<Integer> result = CalculatorOperations.attemptOperation(operands, OPERATION_ADD);
        assertThat(result.get(), is(3));
    }

    @Test
    public void attemptOperationSub() throws Exception {
        Result<Integer> result = CalculatorOperations.attemptOperation(OPERANDS_1_2, OPERATION_SUB);
        assertThat(result.get(), is(-1));
    }

    @Test
    public void attemptOperationMult() throws Exception {
        Result<Integer> result = CalculatorOperations.attemptOperation(OPERANDS_1_2, OPERATION_MULT);
        assertThat(result.get(), is(2));
    }

    @Test
    public void attemptOperationDiv() throws Exception {
        Result<Integer> result = CalculatorOperations.attemptOperation(OPERANDS_1_2, OPERATION_DIV);
        assertThat(result.get(), is(0));
    }

    @Test
    public void attemptOperationDivZero_fails() throws Exception {
        Result<Integer> result = CalculatorOperations.attemptOperation(OPERANDS_1_0, OPERATION_DIV);
        assertFalse(result.isPresent());
        assertTrue(result.failed());
    }
}