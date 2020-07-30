package hexageeks.daftar.views.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import hexageeks.daftar.R;
import hexageeks.daftar.backend.DataProvider;
import hexageeks.daftar.models.ApplicationTemplate;
import hexageeks.daftar.models.User;

public class CreateApplication extends AppCompatActivity {
    private RadioGroup templates_group;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_application);
        snackbar = Snackbar.make(findViewById(android.R.id.content), "Loading ... Please wait...", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Loading...", null).show();
        templates_group = findViewById(R.id.temp_group);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_next);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(CreateApplication.this, ApplicationForm.class);
                User.getInstance().setSelectedTemplate((ApplicationTemplate) findViewById(templates_group.getCheckedRadioButtonId()).getTag());
                startActivity(intent);
            }
        });

        loadData();
    }

    public void loadData() {
        DataProvider.getInstance().getTemplatesData(this, new DataProvider.OnResponse<ApplicationTemplate[]>() {
                    @Override
                    public void execute(ApplicationTemplate[] data) {
                        // This code executes when data is loaded from network
                        for (ApplicationTemplate appTemplate : data) {
                            RadioButton rdbtn = (RadioButton) CreateApplication.this.getLayoutInflater().inflate(R.layout.radio_button, null);
                            rdbtn.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
                            rdbtn.setId(View.generateViewId());
                            rdbtn.setTag(appTemplate);
                            rdbtn.setText(appTemplate.getName());
                            templates_group.addView(rdbtn);
                        }

                        // Task finished
                        // Dismiss Snackbar
                        if (snackbar.isShown())
                            snackbar.dismiss();
                    }
                }
        );
    }
}
