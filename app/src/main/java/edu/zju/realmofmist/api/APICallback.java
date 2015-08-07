package edu.zju.realmofmist.api;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by desolate on 15/8/7.
 */
public interface APICallback {
    public void onSuccess(JSONObject object);
    public void onSuccess(JSONArray array);
    public void onFailure(String description);
}
