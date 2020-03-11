package hexageeks.daftar.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import hexageeks.daftar.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Intent loginIntent = new Intent(this, LoginScreen.class);
        startActivity(loginIntent);
        finish();

    }
}
