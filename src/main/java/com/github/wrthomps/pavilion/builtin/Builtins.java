package com.github.wrthomps.pavilion.builtin;

import java.util.function.BiFunction;
import java.util.function.Function;

import com.github.wrthomps.pavilion.domain.AbsoluteComparator;
import com.github.wrthomps.pavilion.domain.LocationMatchable;
import com.github.wrthomps.pavilion.domain.Matchable;
import com.github.wrthomps.pavilion.domain.SeedDimension;
import com.github.wrthomps.pavilion.geography.GeoCalcUtils;
import com.github.wrthomps.pavilion.geography.LatLong;

/**
 * A utility class for common built-in SeedDimensions and AbsoluteComparators. Most users should
 * find these definitions sufficient.
 *
 * @author wrthomps
 *
 */
public class Builtins
{
    private Builtins()
    {

    }

    /**
     * Gives a SeedDimension comparing the skill of two Matchable entrants in the conventional way.
     * Two entrants are a better fit if the sum of their skill values is closer to 1.0.
     *
     * @return a SeedDimension following the typical convention for skill-based seeding
     */
    public static <T extends Matchable> SeedDimension<T, Double> defaultSkillDimension()
    {
        final double weight = 1.0;
        final Function<T, Double> getSkill = Matchable::getSkill;
        final BiFunction<Double, Double, Double> skillAverager = ( skill1, skill2 ) -> Math.pow( skill1, 2 ) + Math.pow( skill2, 2 );
        final AbsoluteComparator<Double> defaultSkillComparator = Builtins::defaultSkillComparator;

        return new SeedDimension<T, Double>( weight, getSkill, skillAverager, defaultSkillComparator );
    }

    /**
     * A comparison function on Doubles according to standard skill-based seeding conventions. In a
     * good seeding, high-skill entrants play low-skill entrants, and mid-skill entrants play each
     * other. This dimension compares two Doubles by their sum, taking the difference from 1.0, such
     * that two Doubles are a better fit if their sums are closer to 1.0.
     *
     * @param left
     *            A Double representing the skill of a Matchable
     * @param right
     *            A Double representing the skill of a matchable
     * @return a real number in the range [0.0, 1.0] representing the match fitness of the
     *         corresponding Matchables
     */
    public static double defaultSkillComparator( final Double left, final Double right )
    {
        return 1.0 - Math.abs( left + right - 1.0 );
    }

    /**
     * Gives a SeedDimension comparing the location of two entrants in the conventional way. Two
     * entrants are a better fit if they are located physically far from each other.
     *
     * @return a SeedDimension following the typical convention for location-based seeding
     */
    public static <T extends LocationMatchable> SeedDimension<T, LatLong> defaultGeographicDimension()
    {
        final double weight = 1.0;
        final Function<T, LatLong> generator = LocationMatchable::getLatLongOfLocation;
        final BiFunction<LatLong, LatLong, LatLong> midpoint = ( latLong1,
                                                                 latLong2 ) -> new LatLong( distance( latLong1.getLatitude(),
                                                                                                      latLong2.getLatitude() ),
                                                                                            distance( latLong1.getLongitude(),
                                                                                                      latLong2.getLongitude() ) );
        final AbsoluteComparator<LatLong> defaultGeographicComparator = Builtins::defaultGeographicComparator;

        return new SeedDimension<T, LatLong>( weight, generator, midpoint, defaultGeographicComparator );
    }

    private static double distance( final double left, final double right )
    {
        return Math.sqrt( Math.pow( left, 2 ) + Math.pow( right, 2 ) );
    }

    /**
     * A comparison function on LatLongs that gets the great circle distance between them,
     * normalized to a scale of 0.0 to 1.0 by dividing by half the circumference of the Earth.
     *
     * @param firstLatLong
     *            A point on the Earth
     * @param secondLatLong
     *            A point on the Earth
     * @return the distance between the cities as a fraction of half the circumference of the Earth
     */
    public static double defaultGeographicComparator( final LatLong firstLatLong, final LatLong secondLatLong )
    {
        final double greatCircleDistance = GeoCalcUtils.greatCircleDistance( firstLatLong, secondLatLong );
        return greatCircleDistance / ( GeoCalcUtils.EARTH_CIRCUMFERENCE / 2.0 );
    }
}
