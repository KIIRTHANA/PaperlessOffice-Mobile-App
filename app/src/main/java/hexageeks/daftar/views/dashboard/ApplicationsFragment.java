package hexageeks.daftar.views.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hexageeks.daftar.R;
import hexageeks.daftar.backend.DataProvider;
import hexageeks.daftar.models.Application;
import hexageeks.daftar.viewmodels.ApplicationsListAdapter;

public class ApplicationsFragment extends Fragment {

    private Snackbar snackbar;
    private RecyclerView applicationsView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        CoordinatorLayout view = (CoordinatorLayout) inflater.inflate(R.layout.fragment_applications, container, false);

        snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "Loading ... Please wait...", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Loading...", null).show();

        applicationsView = view.findViewById(R.id.applicationsView);

        applicationsView.setHasFixedSize(true);
        applicationsView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadApplicationsData();

        return view;
    }

    void loadApplicationsData() {
        DataProvider.getInstance().getApplcationsData(getActivity(), new DataProvider.OnResponse<Application[]>() {
            @Override
            public void execute(Application[] data) {
                RecyclerView.Adapter applicationsListAdapter = new ApplicationsListAdapter(data);
                applicationsView.setAdapter(applicationsListAdapter);

                if (snackbar.isShown())
                    snackbar.dismiss();
            }
        });
    }
}
