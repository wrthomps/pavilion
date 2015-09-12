package com.github.wrthomps.pavilion.domain;

import com.github.wrthomps.pavilion.geography.LatLong;

/**
 * An interface representing two Matchables that can also be matched based on their geographic
 * location.
 *
 * @author wrthomps
 *
 */
public interface LocationMatchable extends Matchable
{
    /**
     * Gets the latitude and longitude of this LocationMatchable, in degrees.
     *
     * @return the latitude and longitude of this LocationMatchable
     */
    public LatLong getLatLongOfLocation();
}
