
package com.example.go4lunch.Model.Maps;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Southwest implements Serializable
{

    @SerializedName("lat")
    @Expose
    private double lat;
    @SerializedName("lng")
    @Expose
    private double lng;
    private final static long serialVersionUID = 5199988525375487110L;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public Southwest withLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Southwest withLng(double lng) {
        this.lng = lng;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Southwest.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Southwest) == false) {
            return false;
        }
        Southwest rhs = ((Southwest) other);
        return ((Double.doubleToLongBits(this.lng) == Double.doubleToLongBits(rhs.lng))&&(Double.doubleToLongBits(this.lat) == Double.doubleToLongBits(rhs.lat)));
    }

}
