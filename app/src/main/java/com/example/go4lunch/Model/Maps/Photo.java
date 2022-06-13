
package com.example.go4lunch.Model.Maps;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class Photo implements Serializable
{

    @SerializedName("height")
    @Expose
    private int height;
    @SerializedName("html_attributions")
    @Expose
    private List<String> htmlAttributions = null;
    @SerializedName("photo_reference")
    @Expose
    private String photoReference;
    @SerializedName("width")
    @Expose
    private int width;
    private final static long serialVersionUID = 7843244714250835394L;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Photo withHeight(int height) {
        this.height = height;
        return this;
    }

    public List<String> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<String> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public Photo withHtmlAttributions(List<String> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
        return this;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public Photo withPhotoReference(String photoReference) {
        this.photoReference = photoReference;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Photo withWidth(int width) {
        this.width = width;
        return this;
    }

    public String getUrl() {
        String URL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=";
        String key = "&key=";
        return URL + getPhotoReference() + key;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Photo.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("height");
        sb.append('=');
        sb.append(this.height);
        sb.append(',');
        sb.append("htmlAttributions");
        sb.append('=');
        sb.append(((this.htmlAttributions == null)?"<null>":this.htmlAttributions));
        sb.append(',');
        sb.append("photoReference");
        sb.append('=');
        sb.append(((this.photoReference == null)?"<null>":this.photoReference));
        sb.append(',');
        sb.append("width");
        sb.append('=');
        sb.append(this.width);
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
        result = ((result* 31)+ this.width);
        result = ((result* 31)+((this.htmlAttributions == null)? 0 :this.htmlAttributions.hashCode()));
        result = ((result* 31)+((this.photoReference == null)? 0 :this.photoReference.hashCode()));
        result = ((result* 31)+ this.height);
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Photo) == false) {
            return false;
        }
        Photo rhs = ((Photo) other);
        return ((((this.width == rhs.width)&&((this.htmlAttributions == rhs.htmlAttributions)||((this.htmlAttributions!= null)&&this.htmlAttributions.equals(rhs.htmlAttributions))))&&((this.photoReference == rhs.photoReference)||((this.photoReference!= null)&&this.photoReference.equals(rhs.photoReference))))&&(this.height == rhs.height));
    }

}
