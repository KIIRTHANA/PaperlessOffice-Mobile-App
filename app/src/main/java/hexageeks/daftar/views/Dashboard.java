package hexageeks.daftar.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import hexageeks.daftar.R;
import hexageeks.daftar.backend.ServerRequestQueue;
import hexageeks.daftar.models.User;
import hexageeks.daftar.views.dashboard.ApplicationsFragment;
import hexageeks.daftar.views.dashboard.ScanFragment;
import hexageeks.daftar.views.dashboard.StorageFragment;
import hexageeks.daftar.views.dashboard.TabAdapter;

import static hexageeks.daftar.backend.ServerConfig.host;


public class Dashboard extends AppCompatActivity {

    private final String TAG = "DAFTAR: Dashboard:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.options_menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.Settings_menu:
                        break;

                    case R.id.sub_item_logout:
                        SharedPreferences sh = getApplicationContext().getSharedPreferences("UserPref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sh.edit();
                        editor.remove("userToken");
                        editor.commit();
                        logout(User.getInstance().token);
                        User.clearInstance();
                        break;
                }

                return false;
            }
        });


        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), this);
        adapter.addFragment(new ScanFragment(), "Scan");
        adapter.addFragment(new StorageFragment(), "Storage");
        adapter.addFragment(new ApplicationsFragment(), "Applications");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_photo_camera_white_24dp);
        LinearLayout layout = ((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(0));
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
        layoutParams.weight = 0.25f;
        layout.setLayoutParams(layoutParams);

    }


    private void logout(final String token) {
        RequestQueue queue = ServerRequestQueue.getInstance(this).getRequestQueue();

        JsonObjectRequest logoutRequest = new JsonObjectRequest(Request.Method.DELETE, host + "/auth/logout", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Intent loginIntent = new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(loginIntent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    Log.e(TAG, "Failed: " + error
                            + "\nStatus Code " + error.networkResponse.statusCode
                            + "\nCause " + error.getCause()
                            + "\nnetworkResponse " + error.networkResponse.data.toString()
                            + "\nmessage" + error.getMessage());
                } catch (Exception e) {
                    // later
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        queue.add(logoutRequest);
    }

}