package edu.zju.realmofmist.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by desolate on 15/8/7.
 */
@Table(name="Locations")
public class Locations extends Model {
    @Column(name = "latitude", index = true)
    public double latitude;

    @Column(name = "longitude", index = true)
    public double longitude;

    public Locations() {

    }

    public Locations(double latitude, double longitude) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static List<Locations> getAll() {
        return new Select().from(Locations.class).execute();
    }
};