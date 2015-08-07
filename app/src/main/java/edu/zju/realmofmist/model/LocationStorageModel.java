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

    public boolean insertLocation(LocationModel newLocation) {
        if (!existIn(newLocation)) {
            Locations locations = new Locations(newLocation.getLatitude(), newLocation.getLongitude());
            locations.save();
            Log.d("LocationStorageModel", String.format("Save: %f %f", newLocation.getLongitude(), newLocation.getLongitude()));
            locationList.add(newLocation);
            return true;
        }
        return false;
    }

    private boolean existIn(LocationModel newLocation) {
        for (LocationModel ll: locationList) {
            if (ll.isEqual(newLocation)) {
                return true;
            }
        }
        return false;
    }
}
