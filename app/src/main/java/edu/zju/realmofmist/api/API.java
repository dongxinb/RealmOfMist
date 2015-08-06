package edu.zju.realmofmist.api;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by desolate on 15/8/5.
 */
public class API {
    public void signIn(String phone, String code, APICallback callback) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        params.put("code", code);

        RequestBuilder.get("signIn", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        });
    }

    public void requestCaptcha(String phone, APICallback callback) {

    }
}
