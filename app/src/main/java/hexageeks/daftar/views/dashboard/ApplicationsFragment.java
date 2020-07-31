package hexageeks.daftar.views.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import hexageeks.daftar.R;
import hexageeks.daftar.backend.DataProvider;
import hexageeks.daftar.models.Application;
import hexageeks.daftar.viewmodels.ApplicationsListAdapter;

public class ApplicationsFragment extends Fragment {

    private Snackbar snackbar;
    private RecyclerView applicationsView;
    private FloatingActionButton floatingActionButton;
    private SwipeRefreshLayout swipeContainer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        CoordinatorLayout view = (CoordinatorLayout) inflater.inflate(R.layout.fragment_applications, container, false);

        snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "Loading ... Please wait...", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Loading...", null).show();
        applicationsView = view.findViewById(R.id.applicationsView);

        applicationsView.setHasFixedSize(true);
        applicationsView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Apply for new application - Fab
        floatingActionButton = view.findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUploadActivity();

            }
        });

        // Swipe to Refresh
        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDataToRecyclerView();
            }
        });
        // Refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_purple,
                android.R.color.holo_red_dark,
                android.R.color.holo_red_light);

        loadDataToRecyclerView();

        return view;
    }

    void loadDataToRecyclerView() {
        // Data gets loaded
        // This is a sync call
        DataProvider.getInstance().getApplcationsData(getActivity(), new DataProvider.OnResponse<Application[]>() {

                    public void execute(Application[] data) {

                        // This code executes when data is loaded from network
                        // Loads data to recycler view
                        RecyclerView.Adapter applicationsListAdapter = new ApplicationsListAdapter(data, ApplicationsFragment.this.getActivity());
                        applicationsView.setAdapter(applicationsListAdapter);

                        // Task finished
                        if (snackbar.isShown())
                            snackbar.dismiss();

                        // Dismiss SwipeContainer
                        if (swipeContainer.isRefreshing())
                            swipeContainer.setRefreshing(false);
                    }
                }
        );
    }


    public void openUploadActivity() {
        Intent myIntent = new Intent(ApplicationsFragment.this.getActivity(), CreateApplication.class);
        startActivity(myIntent);
    }

}

