package hexageeks.daftar.views.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import hexageeks.daftar.R;

public class UploadFiles extends AppCompatActivity {

    private EditText nameField;
    private EditText descField;
    private RadioGroup visibilityRadioGroup;
    private RadioButton publicRadioButton;
    private RadioButton privateRadioButton;
    private Button selectFileButton;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_files);

        // Form Fields
        nameField = findViewById(R.id.upload_doc_name);
        descField = findViewById(R.id.upload_doc_desc);
    }
}
