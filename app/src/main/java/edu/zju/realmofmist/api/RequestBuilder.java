package edu.zju.realmofmist.api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;

/**
 * Created by desolate on 15/8/7.
 */
public class RequestBuilder {
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static boolean initialized = false;

    private static final String avos_id = "iubzk7650qz83tx89npnph8zjyie4zpcihvk9dlwqf5ww9qt";
    private static final String avos_key = "ojs4lwafvbbleuya0k0j5fkw9a6nibmav8g3mlcesvj3h28c";
    private static final String BASE_URL = "https://leancloud.cn/1.1/functions/";

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        params.setUseJsonStreamer(true);
        if (!initialized) {
            client.addHeader("X-AVOSCloud-Application-Id", avos_id);
            client.addHeader("X-AVOSCloud-Application-Key", avos_key);
            client.addHeader("Content-Type", "application/json; charset=utf-8");
            client.addHeader("X-AVOSCloud-Application-Production", "1");
            initialized = true;
        }
        client.get(getAbsoluteUrl(url), params, responseHandler);

    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        params.setUseJsonStreamer(true);
        if (!initialized) {
            client.addHeader("X-AVOSCloud-Application-Id", avos_id);
            client.addHeader("X-AVOSCloud-Application-Key", avos_key);
            client.addHeader("Content-Type", "application/json; charset=utf-8");
            client.addHeader("X-AVOSCloud-Application-Production", "1");
            initialized = true;
        }
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
