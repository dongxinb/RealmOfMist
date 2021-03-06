package edu.zju.realmofmist.model;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by desolate on 15/8/5.
 */
public class LocationModel {
    private double latitude;
    private double longitude;
    private String updateTime;

    private int errorDegree = 4;

    public LocationModel(double lat, double log, String updTime) {
        latitude = lat;
        longitude = log;
        updateTime = updTime;
    }

    public void setLatitude(double lat) {
        latitude = lat;
    }

    public void setLongitude(double log) {
        longitude = log;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public boolean isEqual(LocationModel otherLocation) {
        double error = Math.pow(10, -errorDegree);
//        Log.d("Locations", error+"");
        if (Math.sqrt((latitude - otherLocation.getLatitude()) * (latitude - otherLocation.getLatitude()) +(longitude - otherLocation.getLongitude())*(longitude - otherLocation.getLongitude())) < error) {
            return true;
        }else {
            return false;
        }

    }

    public LatLng toLatLng() {
        LatLng latLng = new LatLng(latitude, longitude);
        return latLng;
    }

    @Override
    public String toString() {
        return "Latitude: " + latitude + " Longitude: " + longitude + " UpdateTime: " + updateTime;
    }
 }
