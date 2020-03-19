package hexageeks.daftar.views;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import hexageeks.daftar.R;
import hexageeks.daftar.backend.ServerConfig;
import hexageeks.daftar.backend.ServerRequestQueue;

import static hexageeks.daftar.backend.ServerConfig.host;

public class LoginScreen extends Activity {

    private final String TAG = "DAFTAR: LOGIN:";
    private final String loginURL = host + "/auth/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ServerConfig.debugMode(true);

        final EditText usernameBtn = findViewById(R.id.login_username);
        final EditText passBtn = findViewById(R.id.login_password);

        final ProgressBar loading = findViewById(R.id.login_loding);

        Button loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usernameBtn.getText().length() > 1 && passBtn.getText().length() > 1) {

                    loading.setVisibility(View.VISIBLE);

                    HashMap<String, String> jsonReq = new HashMap<>();
                    jsonReq.put("email", String.valueOf(usernameBtn.getText()));
                    jsonReq.put("password", String.valueOf(passBtn.getText()));

                    RequestQueue queue = ServerRequestQueue.getInstance(getApplicationContext()).getRequestQueue();

                    JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, loginURL, new JSONObject(jsonReq), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                String token = response.getString("token");

                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("UserPref", MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                myEdit.clear();
                                myEdit.putString("userToken", token);
                                myEdit.apply();
                                myEdit.commit();

                                Intent dashboardIntent = new Intent(getApplicationContext(), Dashboard.class);
                                startActivity(dashboardIntent);
                                finish();

                                Log.v(TAG, "Login Successful");
                            } catch (JSONException e) {
                                Log.e(TAG, "Failed to parse JSON: " + e);
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

                            Snackbar.make(findViewById(R.id.login_username), "Invalid Credientials, Please try again.", Snackbar.LENGTH_LONG).show();
                            loading.setVisibility(View.GONE);
                        }
                    });

                    queue.add(loginRequest);
                } else {
                    Snackbar.make(findViewById(R.id.login_username), "Please enter email and password.", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

}
