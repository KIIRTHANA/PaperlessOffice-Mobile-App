package hexageeks.daftar.views.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import hexageeks.daftar.R;
import hexageeks.daftar.utils.StorageUtils;

public class UploadFiles extends AppCompatActivity {

    private static final String TAG = "DAFTAR: UploadFiles: ";

    private static final int FILE_SELECT_CODE = 0;

    private EditText nameField;
    private EditText descField;
    private RadioGroup visibilityRadioGroup;
    private RadioButton publicRadioButton;
    private RadioButton privateRadioButton;
    private Button selectFileButton;
    private ImageView previewFile;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_files);

        // Form Fields
        nameField = findViewById(R.id.upload_doc_name);
        descField = findViewById(R.id.upload_doc_desc);
        visibilityRadioGroup = findViewById(R.id.upload_doc_radio_group);
        publicRadioButton = findViewById(R.id.upload_doc_radio_public);
        privateRadioButton = findViewById(R.id.upload_doc_radio_private);
        selectFileButton = findViewById(R.id.upload_doc_file);
        previewFile = findViewById(R.id.upload_doc_preview);
        submitButton = findViewById(R.id.upload_doc_submit_btn);

        selectFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        // TODO: Implement Document Upload Process
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        String [] mimeTypes = {"application/pdf", "image/jpeg", "image/jpg", "image/png"};
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String mimeType = getContentResolver().getType(uri);
                    Log.d(TAG, "File Uri: " + uri.toString() + ", Mime Type: " + mimeType);

                    switch (mimeType) {
                        case "image/jpeg":
                        case "image/jpg":
                        case "image/png":
                            StorageUtils.loadImageFromUri(this, previewFile, uri);
                            break;

                        case "application/pdf":
                            previewFile.setImageResource(R.drawable.pdf_file);
                            break;
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
