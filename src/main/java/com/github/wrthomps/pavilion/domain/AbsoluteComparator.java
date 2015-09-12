package com.github.wrthomps.pavilion.domain;

/**
 * A facility for comparing entrants to determine match fitness.
 *
 * @author wrthomps
 *
 * @param <T>
 *            The entrant type
 */
@FunctionalInterface
public interface AbsoluteComparator<T>
{
    /**
     * Calculate the matchability fitness for a pair of entrants. Minimally-matchable pairs should return 0.0, and maximally-matchable pairs should return 1.0.
     * The implementation of this method <strong>must</strong> be commutative.
     *
     * @param left The first entrant
     * @param right The second entrant
     * @return A value in [0.0, 0.1] representing match fitness.
     */
    public double matchFitness( final T left, final T right );
}
