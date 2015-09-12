package com.github.wrthomps.pavilion.geography;

import static java.lang.Math.abs;
import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class GeoCalcUtils
{
    private static final double EARTH_RADIUS_KILOMETERS = 6371;

    public static final double EARTH_CIRCUMFERENCE = 2 * Math.PI * EARTH_RADIUS_KILOMETERS;

    private GeoCalcUtils()
    {

    }

    /**
     * Calculates the great circle distance between two LatLongs according to the standard simple
     * great circle formula. More information is available here:
     * <a href="https://en.wikipedia.org/wiki/Great-circle_distance#Formulas">Wikipedia</a>
     *
     * @param first
     *            A point on the Earth
     * @param second
     *            A point on the Earth
     * @return the great circle distance between the two points
     */
    public static double greatCircleDistance( final LatLong first, final LatLong second )
    {
        final double longitudeDifference = abs( first.getLongitude() - second.getLongitude() );
        final double centralAngle = acos( sin( first.getLatitude() ) * sin( second.getLatitude() )
                                          + cos( first.getLatitude() ) * cos( second.getLatitude() * cos( longitudeDifference ) ) );

        return EARTH_RADIUS_KILOMETERS * centralAngle;
    }

    /**
     * Uses the GeoNames city database to look up the latitude and longitude of a city given its
     * name.
     *
     * @param city
     *            The name of the city to look up
     * @return the latitude and longitude of the city
     */
    public static LatLong getLatitudeLongitudeFromCityName( final String city )
    {
        // TODO: Implement using Geonames
        return new LatLong( 0, 0 );
    }
}
