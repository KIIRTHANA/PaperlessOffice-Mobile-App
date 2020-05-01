package hexageeks.daftar.backend;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import hexageeks.daftar.models.StorageItem;
import hexageeks.daftar.models.User;

import static hexageeks.daftar.backend.ServerConfig.host;



public class DataProvider {

    private final String TAG = "DAFTAR: DATAPROVIDER:";

    public static DataProvider instance = null;


    public static DataProvider getInstance() {
        if (instance == null) {
            return newInstance();
        }

        return instance;
    }

    public static DataProvider newInstance() {
        return instance = new DataProvider();
    }

    public void getStorageData(Context context, final DataProvider.OnResponse r) {
        RequestQueue queue = ServerRequestQueue.getInstance(context).getRequestQueue();

        JsonArrayRequest loginRequest = new JsonArrayRequest(Request.Method.GET, host + "/storage", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                try {
                    StorageItem[] data = new StorageItem[response.length()];

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = response.getJSONObject(i);

                        String timestamp = obj.getJSONObject("timestamp").getString("$date");
                        Date d = new Date(Long.parseLong(timestamp));

                        data[i] = new StorageItem(obj.getJSONObject("_id").getString("$oid"),
                                obj.getString("fileName"), obj.getString("fileDescription"),
                                null, obj.getString("visibility"), obj.getString("creator"),
                                d);
                    }

                    Log.v(TAG, "Storage Data Retrieved ");

                    r.execute(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Failed: " + error
                        + "\nStatus Code " + error.networkResponse.statusCode
                        + "\nCause " + error.getCause()
                        + "\nnetworkResponse " + error.networkResponse.data.toString()
                        + "\nmessage" + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                // Basic Authentication
                //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);

                headers.put("Authorization", "Bearer " + User.instance.token);
                return headers;
            }
        };

        queue.add(loginRequest);
    }

    public interface OnResponse<G> {
        void execute(G data);
    }
}
