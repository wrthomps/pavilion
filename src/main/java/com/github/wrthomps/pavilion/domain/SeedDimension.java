package com.github.wrthomps.pavilion.domain;

import java.util.function.BiFunction;
import java.util.function.Function;

import lombok.Data;

/**
 * A single dimension on which to evaluate entrants. Clients must provide the following:
 * <ul>
 * <li>A <em>weight</em> from 0.0 to 1.0 representing how heavily this dimension should be weighted
 * in seeding</li>
 * <li>A <em>generator</em> function for getting property values out of entrants
 * <li>A <em>fitness comparator</em> on the property type to determine how matchable two entrants
 * are along this dimension
 * </ul>
 *
 * @author wrthomps
 *
 * @param <T>
 *            The type of entrant
 * @param <U>
 *            The type of the property on which to evaluate entrants
 */
@Data
public class SeedDimension<T extends Matchable, U>
{
    private final double weight;
    private final Function<T, U> generator;
    private final BiFunction<U, U, U> propertyAverager;
    private final AbsoluteComparator<U> fitnessComparator;
}
