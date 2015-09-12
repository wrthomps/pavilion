package com.github.wrthomps.pavilion.geography;

import lombok.Data;

/**
 * Represents the latitude and longitude of a point on the Earth, in radians.
 *
 * @author wrthomps
 *
 */
@Data
public class LatLong
{
    private final double latitude;
    private final double longitude;
}
