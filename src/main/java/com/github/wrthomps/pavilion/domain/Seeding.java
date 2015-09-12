package com.github.wrthomps.pavilion.domain;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.github.wrthomps.pavilion.builtin.Builtins;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A seeding of entrants in a tournament.
 *
 * @author wrthomps
 *
 * @param <T>
 *            The type of entrant
 */
@AllArgsConstructor
@Builder
public class Seeding<T extends Matchable>
{
    private final Set<SeedDimension<T, ?>> dimensions;
    private final SeedingSearch<T> seedingSearch;

    public static <T extends Matchable> Seeding<T> byDefaultSkill()
    {
        return Seeding.<T>builder()
                      .dimensions( ImmutableSet.of( Builtins.defaultSkillDimension() ) )
                      .seedingSearch( Seeding.defaultSeedingSearch() )
                      .build();
    }

    private static <T extends Matchable> SeedingSearch<T> defaultSeedingSearch()
    {
        // TODO: Implement a default full simulated annealing process
        return new SeedingSearch<T>()
        {
            @Override
            public boolean canContinue()
            {
                return false;
            }

            @Override
            public List<T> getNextCandidate( final List<T> entrants, final double currentFitness )
            {
                return entrants;
            }
        };
    }

    public ImmutableList<T> getSeededEntrants( final Set<T> entrants )
    {
        List<T> candidateSeeding = ImmutableList.copyOf( entrants );
        double currentFitness = 0.0;

        while ( this.seedingSearch.canContinue() )
        {
            candidateSeeding = this.seedingSearch.getNextCandidate( candidateSeeding, currentFitness );
            currentFitness = this.getCurrentFitness( candidateSeeding );
        }

        return ImmutableList.copyOf( candidateSeeding );
    }

    private double getCurrentFitness( final List<T> entrants )
    {
        if ( entrants.isEmpty() || entrants.size() == 1 )
        {
            return 0.0;
        }

        double fitness = 0.0;
        for ( final SeedDimension<T, ?> dimension : this.dimensions )
        {
            fitness += this.getFitnessInOneDimension( entrants, dimension );
        }

        // TODO: Recursively evaluate "expected" winning entrants to correctly order for subsequent
        // rounds
        return fitness;
    }

    private <U> double getFitnessInOneDimension( final List<T> entrants, final SeedDimension<T, U> dimension )
    {
        double fitness = 0;
        for ( int i = 0; i < entrants.size(); i += 2 )
        {
            final T firstEntrant = entrants.get( i );
            final T secondEntrant = entrants.get( i + 1 );

            final AbsoluteComparator<U> fitnessComparator = dimension.getFitnessComparator();
            final Function<T, U> generator = dimension.getGenerator();
            fitness += fitnessComparator.matchFitness( generator.apply( firstEntrant ), generator.apply( secondEntrant ) );
        }

        return fitness;
    }
}
