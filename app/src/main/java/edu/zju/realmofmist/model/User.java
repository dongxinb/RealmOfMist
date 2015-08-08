package edu.zju.realmofmist.model;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import edu.zju.realmofmist.api.APICallback;
import edu.zju.realmofmist.api.RequestBuilder;

/**
 * Created by desolate on 15/8/7.
 */
public class User {
    public static User currentUser = null;
    public static User getCurrentUser() {
        if (currentUser == null) {
            synchronized (User.class) {
                if (currentUser == null) {
                    currentUser = new User();
                }
            }
        }
        return currentUser;
    }
    private String mId;
    private String mName;
    private double mArea;

    public static void signIn(String phone, String zone, String code, final APICallback callback) {
        RequestParams params = new RequestParams();
        params.put("mobile", phone);
        params.put("code", code);
        params.put("zone", zone);

        RequestBuilder.post("signin", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject res = response.getJSONObject("result");
                    if (res.getInt("code") == 200) {
                        User.getCurrentUser().mId = res.getJSONObject("data").getString("id");
                        User.getCurrentUser().mName = res.getJSONObject("data").getString("username");
                        User.getCurrentUser().mArea = 0;
                        callback.onSuccess(res.getJSONObject("data"));
                    } else {
                        callback.onFailure(res.getString("description"));
                    }
                } catch (Exception e) {
                    Log.d("API", e.getMessage());
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println(errorResponse);
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    public void updateArea() {
        RequestParams params = new RequestParams();
        params.put("userId", User.getCurrentUser().getId());
        params.put("area", User.getCurrentUser().getArea());
        if (User.getCurrentUser().getId() != null && User.getCurrentUser().getId().length() != 0) {
            RequestBuilder.post("updateArea", params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                }
            });
        }

    }

    public void signOut() {
        User.getCurrentUser().mId = null;
        User.getCurrentUser().mName = null;
        User.getCurrentUser().mArea = 0;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public double getArea() {
        return mArea;
    }

    public void setArea(double area) {
        mArea = area;
    }
}
