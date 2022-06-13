
package com.example.go4lunch.Model.Maps;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.SphericalUtil;

import java.io.Serializable;


public class Location implements Serializable
{

    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;
    private final static long serialVersionUID = 1246056559391112331L;

    public Location(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Location() {}

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Location withLat(Double lat) {
        this.lat = lat;
        return this;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }



    public static Double computeDistance(Location startLoc, Location endLoc) {
        LatLng from = new LatLng(startLoc.getLat(), startLoc.getLng());
        LatLng to = new LatLng(endLoc.getLat(), endLoc.getLng());

        Double dis = SphericalUtil.computeDistanceBetween(from, to);

        return dis;
    }




    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Location.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("lat");
        sb.append('=');
        sb.append(this.lat);
        sb.append(',');
        sb.append("lng");
        sb.append('=');
        sb.append(this.lng);
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((int)(Double.doubleToLongBits(this.lng)^(Double.doubleToLongBits(this.lng)>>> 32))));
        result = ((result* 31)+((int)(Double.doubleToLongBits(this.lat)^(Double.doubleToLongBits(this.lat)>>> 32))));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Location) == false) {
            return false;
        }
        Location rhs = ((Location) other);
        return ((Double.doubleToLongBits(this.lng) == Double.doubleToLongBits(rhs.lng))&&(Double.doubleToLongBits(this.lat) == Double.doubleToLongBits(rhs.lat)));
    }

}
