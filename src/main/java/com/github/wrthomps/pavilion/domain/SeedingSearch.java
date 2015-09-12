package com.github.wrthomps.pavilion.domain;

import java.util.List;

public interface SeedingSearch<T extends Matchable>
{
    boolean canContinue();

    List<T> getNextCandidate( final List<T> entrants, final double currentFitness );
}
