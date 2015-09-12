package com.github.wrthomps.pavilion.domain;

/**
 * The primary interface used in the Pavilion matching system. Any class implementing this interface
 * can be seeded. It specifies a single method returning the player's skill, ranging from 0.0
 * (lowest) to 1.0 (highest).
 *
 * @author wrthomps
 */
public interface Matchable
{
    /**
     * Gets the matchable entrant's skill at a contest, ranging from a minimum 0.0 to maximum 1.0.
     *
     * @return the entrant's skill value
     */
    double getSkill();
}
