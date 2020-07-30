package hexageeks.daftar.views.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import hexageeks.daftar.R;
import hexageeks.daftar.backend.DataProvider;
import hexageeks.daftar.models.StorageItem;
import hexageeks.daftar.models.User;
import hexageeks.daftar.utils.StorageUtils;

public class DocDetails extends AppCompatActivity {
    private Snackbar snackbar;
    private ImageView docPreview;
    private TextView docName;
    private TextView docDesc;
    private TextView docType;
    private TextView docDate;
    private TextView visibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_details);

        snackbar = Snackbar.make(this.findViewById(android.R.id.content), "Loading ... Please wait...", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Loading...", null).show();
        String docId = User.getInstance().getSelectedDoc();

        docPreview = this.findViewById(R.id.doc_preview);
        docName = this.findViewById(R.id.doc_name);
        docDesc = this.findViewById(R.id.doc_desc);
        docType = this.findViewById(R.id.doc_type);
        docDate = this.findViewById(R.id.doc_date);
        visibility = this.findViewById(R.id.doc_visibility);

        loadData(docId);
    }

    void loadData(String docId) {
        // Data gets loaded
        // This is a sync call
        DataProvider.getInstance().getDocumentData(this, docId, new DataProvider.OnResponse<StorageItem>() {
                    @Override
                    public void execute(StorageItem data) {
                        // This code executes when data is loaded from network
                        // Load data to recycler view

                        // TODO: Bringup preview of FileType.PDF
                        switch (data.getFileType()) {
                            case IMAGE:
                                StorageUtils.loadImageFromUrl(DocDetails.this, docPreview, data.getFileUrl());
                                break;

                            default:
                                docPreview.setImageResource(R.drawable.pdf_file);
                        }

                        docName.setText(data.getFileName());
                        docDesc.setText(data.getFileDescription());
                        docType.setText(data.getFileType().toString());
                        docDate.setText(data.getTimestamp().toString());
                        visibility.setText(data.getVisibility());

                        // Task finished
                        // Dismiss Snackbar
                        if (snackbar.isShown())
                            snackbar.dismiss();

                    }
                }
        );
    }
}
