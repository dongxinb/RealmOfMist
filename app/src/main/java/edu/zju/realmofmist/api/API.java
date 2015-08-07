package edu.zju.realmofmist.api;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by desolate on 15/8/5.
 */
public class API {

    public static void getAreaList(String userId, final APICallback callback) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        RequestBuilder.post("areaList", params, new JsonHttpResponseHandler()  {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject res = response.getJSONObject("result");
                    if (res.getInt("code") == 200) {
                        callback.onSuccess(res.getJSONArray("data"));
                    }else {
                        callback.onFailure(res.getString("description"));
                    }
                }catch (Exception e) {
                    System.out.println(e);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                callback.onFailure("Network Error!");
            }
        });

    }
}
