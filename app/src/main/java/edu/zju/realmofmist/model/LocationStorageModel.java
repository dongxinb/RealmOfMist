package edu.zju.realmofmist.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andying on 8/7/15.
 */
public class LocationStorageModel {
    private List<LocationModel> locationList;

    public LocationStorageModel() {
        locationList = new ArrayList<LocationModel>();
        locationList.clear();
        List<Locations> ll = Locations.getAll();

        for (Locations res: ll) {
            LocationModel newLocation = new LocationModel(res.latitude, res.longitude, null);
            locationList.add(newLocation);
        }
        Log.d("LocationStorageModel", String.format("%d", locationList.size()));
    }

    public int getSize() {
        return locationList.size();
    }

    public List<LocationModel> getLocationList() {
        return locationList;
    }

    public LocationModel getLocation(int position) {
        return locationList.get(position);
    }

    public void insertLocation(double latitude, double longitude, String updateTime) {
        int size = locationList.size();
        LocationModel newLocation = new LocationModel(latitude, longitude, updateTime);

        for (int i = 0; i < size; i++) {
            if (locationList.get(i).isEqual(newLocation) == true) {
                return;
            }else {
                Locations locations = new Locations(latitude, longitude);
                locations.save();
            }
        }

        locationList.add(newLocation);
    }

    public void insertLocation(LocationModel newLocation) {
        int size = locationList.size();
        for (int i = 0; i < size; i++) {
            if (locationList.get(i).isEqual(newLocation) == true) {
                return ;
            }else {
                Locations locations = new Locations(newLocation.getLatitude(), newLocation.getLongitude());
                locations.save();
            }
        }

        locationList.add(newLocation);
    }
}
