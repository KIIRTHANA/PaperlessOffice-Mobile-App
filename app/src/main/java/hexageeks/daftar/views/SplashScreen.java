package hexageeks.daftar.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import hexageeks.daftar.R;
import hexageeks.daftar.backend.ServerRequestQueue;
import hexageeks.daftar.models.User;

import static hexageeks.daftar.backend.ServerConfig.host;

public class SplashScreen extends AppCompatActivity {

    private final String TAG = "DAFTAR: SPLASH:";
    private final String url = host + "/user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sh = getApplicationContext().getSharedPreferences("UserPref", MODE_PRIVATE);
        final String token = sh.getString("userToken", null);

        if (token != null) {
            RequestQueue queue = ServerRequestQueue.getInstance(getApplicationContext()).getRequestQueue();

            JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        User.setInstance(response.getJSONObject("_id").getString("$oid"), response.getString("first_name"),
                                response.getString("last_name"), response.getString("dob"),
                                response.getString("role"));

                        Intent dashboardIntent = new Intent(getApplicationContext(), Dashboard.class);
                        startActivity(dashboardIntent);
                        finish();

                        Log.v(TAG, "Login Successful: ");
                    } catch (JSONException e) {
                        e.printStackTrace();

                        Intent loginIntent = new Intent(getApplicationContext(), LoginScreen.class);
                        startActivity(loginIntent);
                        finish();
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

                    Intent loginIntent = new Intent(getApplicationContext(), LoginScreen.class);
                    startActivity(loginIntent);
                    finish();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    // Basic Authentication
                    //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);

                    headers.put("Authorization", "Bearer " + token);
                    return headers;
                }
            };

            queue.add(loginRequest);
        } else {
            Intent loginIntent = new Intent(this, LoginScreen.class);
            startActivity(loginIntent);
            finish();
        }
    }
}
