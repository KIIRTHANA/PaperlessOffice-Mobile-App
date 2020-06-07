package hexageeks.daftar.backend;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import hexageeks.daftar.models.Application;
import hexageeks.daftar.models.StorageItem;
import hexageeks.daftar.models.User;
import hexageeks.daftar.utils.StorageUtils;

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
                                obj.getString("fileExtension"),
                                StorageUtils.parseFileType(obj.getString("fileExtension")), obj.getString("fileName"),
                                obj.getString("fileDescription"),
                                obj.getString("visibility"), obj.getString("creator"),
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

    public void addDocument(final Context context, final String fileName, final String desc,
                            final String visibility, final InputStream inputStream,
                            final String mimetype, final Uri uri, final DataProvider.OnFinished r) {

        final VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, host + "/storage", new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Log.i(TAG, "Added New Document");
                r.execute();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    Log.v(TAG, result);
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("fileName", fileName);
                params.put("fileDescription", desc);
                params.put("visibility", visibility);

                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                try {
                    params.put("file", new DataPart(StorageUtils.getNameFromURI(uri, context),
                            StorageUtils.InputStreamToBytes(inputStream), mimetype));
                } catch (IOException e) {
                    Log.e(TAG, "Error Converting Input Stream to Bytes");
                    e.printStackTrace();
                }

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("Authorization", "Bearer " + User.instance.token);
                return headers;
            }
        };

        ServerRequestQueue.getInstance(context).getRequestQueue().add(multipartRequest);
    }

    public void getApplcationsData(Context context, final DataProvider.OnResponse r) {
        RequestQueue queue = ServerRequestQueue.getInstance(context).getRequestQueue();

        JsonArrayRequest loginRequest = new JsonArrayRequest(Request.Method.GET, host + "/applications", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Application[] data = new Application[response.length()];

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = response.getJSONObject(i);

                        String timestamp = obj.getJSONObject("timestamp").getString("$date");
                        Date d = new Date(Long.parseLong(timestamp));

                        data[i] = new Application(obj.getJSONObject("_id").getString("$oid"),
                                obj.getString("name"), obj.getString("description"),
                                obj.getString("message"), obj.getString("templateId"),
                                obj.getString("creatorId"), obj.getString("creatorName"),
                                obj.getString("workflowId"), obj.getString("assignedId"),
                                obj.getString("formId"), obj.getInt("status"),
                                obj.getInt("stage"), obj.getInt("stages"),
                                d);
                    }

                    Log.v(TAG, "Application Data Retrieved ");

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

    public interface OnFinished {
        void execute();
    }
}
