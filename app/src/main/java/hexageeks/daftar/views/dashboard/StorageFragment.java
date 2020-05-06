package hexageeks.daftar.views.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hexageeks.daftar.R;
import hexageeks.daftar.backend.DataProvider;
import hexageeks.daftar.models.StorageItem;
import hexageeks.daftar.viewmodels.StorageAdapter;

public class StorageFragment extends Fragment {
    private Snackbar snackbar;
    private RecyclerView storageRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_storage, container, false);
        snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "Loading ... Please wait...", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Loading...", null).show();

        storageRecyclerView = view.findViewById(R.id.storage_recycler);

        storageRecyclerView.setHasFixedSize(true);
        storageRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        loadDataToRecyclerView();

        return view;
    }

    void loadDataToRecyclerView() {
        // Data gets loaded
        DataProvider.getInstance().getStorageData(getActivity(), new DataProvider.OnResponse<StorageItem[]>() {
                @Override
                public void execute(StorageItem[] data) {
                    RecyclerView.Adapter storageAdapter = new StorageAdapter(data);
                    storageRecyclerView.setAdapter(storageAdapter);

                    if (snackbar.isShown())
                        snackbar.dismiss();
                }
            }

        );
    }



}

