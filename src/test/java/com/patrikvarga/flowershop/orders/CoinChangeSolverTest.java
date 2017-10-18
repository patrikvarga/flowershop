package com.patrikvarga.flowershop.orders;

import java.util.List;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CoinChangeSolverTest {

    @Test
    public void emptyListSolvesZeroAmount() {
        final List<List<Integer>> solutions = CoinChangeSolver.change(
                new Integer[]{1, 2, 3}, i -> i, 0);

        assertThat(solutions.size(), is(1));
        assertThat(solutions.get(0), is(emptyList()));
    }

    @Test
    public void emptyListSolvesZeroAmountEvenWithoutCoins() {
        final List<List<Integer>> solutions = CoinChangeSolver.change(
                new Integer[]{}, i -> i, 0);

        assertThat(solutions.size(), is(1));
        assertThat(solutions.get(0), is(emptyList()));
    }

    @Test
    public void oneCoinSolvesTheSameAmount() {
        final List<List<Integer>> solutions = CoinChangeSolver.change(
                new Integer[]{5}, i -> i, 5);

        assertThat(solutions.size(), is(1));
        assertThat(solutions.get(0), is(asList(5)));
    }

    @Test
    public void oneCoinSolvesMultipliedAmount() {
        final List<List<Integer>> solutions = CoinChangeSolver.change(
                new Integer[]{5}, i -> i, 15);

        assertThat(solutions.size(), is(1));
        assertThat(solutions.get(0), is(asList(5, 5, 5)));
    }

    @Test
    public void oneCoinCannotSolveLessAmount() {
        final List<List<Integer>> solutions = CoinChangeSolver.change(
                new Integer[]{5}, i -> i, 4);

        assertThat(solutions.size(), is(0));
    }

}
