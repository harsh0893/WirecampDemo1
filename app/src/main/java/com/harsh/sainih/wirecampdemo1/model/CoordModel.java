package com.harsh.sainih.wirecampdemo1.model;

/** coordmodel to store geo coordinates from the location in the API response
 * Created by sainih on 11/1/2017.
 */

public class CoordModel {

    private double lon;
    private double lat;

    public CoordModel(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public CoordModel() {
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
