package com.patrikvarga.flowershop.orders;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import static java.util.Collections.emptyList;

/**
 * A generic coin change problem solver.
 *
 * Instead of dealing with integers, we introduce the denomination function so that denominations of various types can
 * be used. This makes it possible to find a solution of flower bundles in one step.
 *
 * @author <a href="mailto:varga.patrik@gmail.com">Patrik Varga</a>
 */
public class CoinChangeSolver {

    /**
     * Find an optimal solution, i.e. one with minimum number of denominations.
     *
     * @param <T> denomination
     * @param denom denominations
     * @param denomFunction denomination value reader function
     * @param amount target sum amount
     * @return a single optimal solution with the used denominations
     */
    public static <T> List<T> minCange(final T[] denom, final Function<T, Integer> denomFunction, final int amount) {
        final List<List<T>> result = change(denom, denomFunction, amount);
        if (result.isEmpty()) {
            return emptyList();
        }
        result.sort(Comparator.comparingInt(List::size));
        return result.get(0);
    }

    /**
     * Find all possible solutions.
     *
     * @param <T> denomination
     * @param denom denominations
     * @param denomFunction denomination value reader function
     * @param amount target sum amount
     * @return all possible solutions
     */
    public static <T> List<List<T>> change(
            final T[] denom,
            final Function<T, Integer> denomFunction,
            final int amount
    ) {
        final List<List<T>> solutions = new ArrayList<>();
        change(denom, denomFunction, amount, new ArrayList<>(), 0, solutions);
        return solutions;
    }

    private static <T> void change(
            final T[] denom,
            final Function<T, Integer> denomFunction,
            final int remaining,
            final List<T> coins,
            final int pos,
            final List<List<T>> solutions
    ) {
        if (remaining == 0) {
            solutions.add(new ArrayList<>(coins));
        } else {
            final T currentDenom = denom[pos];
            final int currentDenomValue = denomFunction.apply(currentDenom);
            if (remaining >= currentDenomValue) {
                coins.add(currentDenom);
                change(denom, denomFunction, remaining - currentDenomValue, coins, pos, solutions);
                coins.remove(coins.size() - 1);
            }
            if (pos + 1 < denom.length) {
                change(denom, denomFunction, remaining, coins, pos + 1, solutions);
            }
        }
    }

}
