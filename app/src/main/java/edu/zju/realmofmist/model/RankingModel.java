package edu.zju.realmofmist.model;

/**
 * Created by Soa on 15/8/6.
 */
public class RankingModel {
    private String mName;
    private double mArea;

    public RankingModel(String name, double area) {
        mName = name;
        mArea = area;
    }

    public String getName() {
        return mName;
    }

    public double getArea() {
        return mArea;
    }


}
