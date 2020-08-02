package hexageeks.daftar.views;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import hexageeks.daftar.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;


public class PinActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        setTitle("Enter Pin");

        final Pinview pin = findViewById(R.id.pinview);
        Button verifyBtn = findViewById(R.id.verify_pin);

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pin.getValue().equalsIgnoreCase("123456")) {
                    Intent loginIntent = new Intent(getApplicationContext(), Dashboard.class);
                    startActivity(loginIntent);
                    finish();
                } else {
                    Toast.makeText(PinActivity.this, "Please enter correct pin.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
