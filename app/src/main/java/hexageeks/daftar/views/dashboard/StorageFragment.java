package hexageeks.daftar.views.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import hexageeks.daftar.R;
import hexageeks.daftar.backend.DataProvider;
import hexageeks.daftar.models.StorageItem;
import hexageeks.daftar.viewmodels.StorageAdapter;

public class StorageFragment extends Fragment {
    private Snackbar snackbar;
    private RecyclerView storageRecyclerView;
    private ExtendedFloatingActionButton extendedFloatingActionButton;
    private SwipeRefreshLayout swipeContainer;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        CoordinatorLayout view = (CoordinatorLayout) inflater.inflate(R.layout.fragment_storage, container, false);
        snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "Loading ... Please wait...", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Loading...", null).show();

        storageRecyclerView = view.findViewById(R.id.storage_recycler);

        storageRecyclerView.setHasFixedSize(true);
        storageRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        // Upload file - FAB
        extendedFloatingActionButton = view.findViewById(R.id.fab_upload);
        extendedFloatingActionButton.setOnClickListener(new View.OnClickListener() {
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
        DataProvider.getInstance().getStorageData(getActivity(), new DataProvider.OnResponse<StorageItem[]>() {
                    @Override
                    public void execute(StorageItem[] data) {
                        // This code executes when data is loaded from network
                        // Load data to recycler view
                        RecyclerView.Adapter storageAdapter = new StorageAdapter(data);
                        storageRecyclerView.setAdapter(storageAdapter);

                        // Task finished
                        // Dismiss Snackbar
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
        Intent myIntent = new Intent(StorageFragment.this.getActivity(), UploadFiles.class);
        startActivity(myIntent);
    }


}


