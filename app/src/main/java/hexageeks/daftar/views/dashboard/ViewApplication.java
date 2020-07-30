package hexageeks.daftar.views.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hexageeks.daftar.R;
import hexageeks.daftar.backend.DataProvider;
import hexageeks.daftar.models.Application;
import hexageeks.daftar.models.User;
import hexageeks.daftar.viewmodels.ApplicationsListAdapter;
import hexageeks.daftar.viewmodels.WorkflowListAdapter;

public class ViewApplication extends AppCompatActivity {

    private Snackbar snackbar;
    private RecyclerView workflowView;
    private TextView applicationTitle;
    private TextView applicationDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_application);

        String docId = User.getInstance().getSelectedApp();

        applicationTitle = findViewById(R.id.view_app_title);
        applicationDesc = findViewById(R.id.view_app_desc);

        snackbar = Snackbar.make(this.findViewById(android.R.id.content), "Loading ... Please wait...", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Loading...", null).show();
        workflowView = this.findViewById(R.id.workflowView);

        workflowView.setHasFixedSize(true);
        workflowView.setLayoutManager(new LinearLayoutManager(this));

        loadData(docId);
    }

    void loadData(final String docId) {
        DataProvider.getInstance().getApplicationData(this, docId, new DataProvider.OnResponse<Application>() {
            @Override
            public void execute(Application data) {
                applicationTitle.setText(data.getName());
                applicationDesc.setText("Created by " + data.getCreatorName() + " on \n" + data.getTimestamp().toString());
                RecyclerView.Adapter applicationsListAdapter = new WorkflowListAdapter(data, ViewApplication.this);
                workflowView.setAdapter(applicationsListAdapter);

                if (snackbar.isShown())
                    snackbar.dismiss();
            }
        });
    }
}
