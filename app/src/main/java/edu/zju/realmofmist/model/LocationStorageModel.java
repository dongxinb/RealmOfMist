package edu.zju.realmofmist.model;

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
    }

    public int getSize() {
        return locationList.size();
    }

    public LocationModel getLocation(int position) {
        return locationList.get(position);
    }

    public void insertLocation(double latitude, double longitude, String updateTime) {
        int size = locationList.size();
        LocationModel newLocation = new LocationModel(latitude, longitude, updateTime);

        for (int i = 0; i < size; i++) {
            if (locationList.get(i).isEqual(newLocation) == true)
                return;
        }

        locationList.add(newLocation);
    }

    public void insertLocation(LocationModel newLocation) {
        int size = locationList.size();
        for (int i = 0; i < size; i++) {
            if (locationList.get(i).isEqual(newLocation) == true)
                return;
        }

        locationList.add(newLocation);
    }
}
