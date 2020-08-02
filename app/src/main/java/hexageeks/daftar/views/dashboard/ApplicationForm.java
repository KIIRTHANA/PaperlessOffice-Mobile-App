package hexageeks.daftar.views.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import hexageeks.daftar.R;
import hexageeks.daftar.backend.DataProvider;
import hexageeks.daftar.models.ApplicationTemplate;
import hexageeks.daftar.models.Form;
import hexageeks.daftar.models.User;
import hexageeks.daftar.views.Dashboard;

public class ApplicationForm extends AppCompatActivity {

    private ApplicationTemplate appTemp;
    private Button submit;
    private TextView formName;
    private LinearLayout formLayout;
    private HashMap<String, String> fields = new HashMap<>();
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_form);
        appTemp = User.getInstance().getSelectedTemplate();
        setTitle(appTemp.getName());

        submit = findViewById(R.id.form_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    submit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        formName = findViewById(R.id.form_name);
        formLayout = findViewById(R.id.form_layout);
        snackbar = Snackbar.make(findViewById(android.R.id.content), "Please wait...", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Please Wait...", null);

        loadForm(appTemp.getFormId());
    }

    public void loadForm(String formId) {
        DataProvider.getInstance().loadFormsData(this, formId, new DataProvider.OnResponse<Form>() {
            @Override
            public void execute(Form data) {
                formName.setText(data.getTitle());
            }
        }, new DataProvider.OnEachResponse<JSONObject>() {
            @Override
            public void execute(JSONObject data) throws JSONException {
                fields.put(data.getString("question"), null);
                switch (data.getString("type")) {
                    case "text":
                    case "Date":
                    case "Email":
                    case "Number":
                    case "long_answer": displayTextField(data.getString("type"), data.getString("question")); break;
                    case "radio": displayRadio(data.getString("question"), data.getJSONObject("options")); break;
                    case "checkbox": displayCheckbox(data.getString("question"), data.getJSONObject("options")); break;
                }
            }
        });
    }

    public void displayTextField(String type, String question) {
        LinearLayout l = (LinearLayout) getLayoutInflater().inflate(R.layout.form_text_field, null);
        TextView q = l.findViewById(R.id.q);
        EditText a = l.findViewById(R.id.a);

        switch(type) {
            case "Number": a.setInputType(InputType.TYPE_CLASS_NUMBER); break;
            case "Date": a.setInputType(InputType.TYPE_CLASS_DATETIME); break;
            case "Email": a.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS); break;
            case "long_answer": a.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE); break;
        }

        q.setText(question);

        q.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        a.setTag(question);
        formLayout.addView(l);
    }

    public void displayRadio(String question, JSONObject options) throws JSONException {
        LinearLayout l = (LinearLayout) getLayoutInflater().inflate(R.layout.form_radio_field, null);
        TextView q = l.findViewById(R.id.q);
        RadioGroup rg = l.findViewById(R.id.rg);

        Iterator<String> itr = options.keys();
        while(itr.hasNext()) {
            String a = options.getString(itr.next());
            RadioButton rdbtn = new RadioButton(this);
            rdbtn.setId(View.generateViewId());
            rdbtn.setText(a);
            rg.addView(rdbtn);
        }

        q.setText(question);
        rg.setTag(question);
        formLayout.addView(l);
    }

    public void displayCheckbox(final String question, JSONObject options) throws JSONException {
        LinearLayout l = (LinearLayout) getLayoutInflater().inflate(R.layout.form_checkbox_field, null);
        TextView q = l.findViewById(R.id.q);

        Iterator<String> itr = options.keys();
        while(itr.hasNext()) {
            String a = options.getString(itr.next());
            CheckBox c = new CheckBox(this);
            c.setId(View.generateViewId());
            c.setText(a);
            c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        fields.put(question, buttonView.getText().toString());
                    }
                }
            });
            l.addView(c);
        }

        q.setText(question);
        formLayout.addView(l);
    }

    public void submit() throws JSONException {
        snackbar.show();
        JSONObject form = new JSONObject();

        Iterator<String> itr = fields.keySet().iterator();
        while(itr.hasNext()) {
            String q = itr.next();
            String v = fields.get(q);

            if (v == null) {
                View view = formLayout.findViewWithTag(q);

                if (view.getClass().getName().equalsIgnoreCase("android.widget.RadioGroup")) {
                    v = ((RadioButton) findViewById(((RadioGroup) view).getCheckedRadioButtonId())).getText().toString();
                } else {
                    v = ((EditText) view).getText().toString();
                }
            }

            form.put(q, v);
        }

        DataProvider.getInstance().addApplication(this, appTemp.getName(), appTemp.getId(), form, new DataProvider.OnFinished() {
            @Override
            public void execute() {
                snackbar.dismiss();
            }
        });
    }
}
