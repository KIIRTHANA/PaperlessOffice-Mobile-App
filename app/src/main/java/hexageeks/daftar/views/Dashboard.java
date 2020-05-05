package hexageeks.daftar.views;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import hexageeks.daftar.R;
import hexageeks.daftar.models.User;
import hexageeks.daftar.views.dashboard.ApplicationsFragment;
import hexageeks.daftar.views.dashboard.ScanFragment;
import hexageeks.daftar.views.dashboard.StorageFragment;
import hexageeks.daftar.views.dashboard.TabAdapter;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.Settings_menu:

                        break;
                }

                return false;
            }
        });

        ViewPager  viewPager = findViewById(R.id.view_pager);
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

    }}
// STORAGE view - Delete button

