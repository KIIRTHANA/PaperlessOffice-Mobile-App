package hexageeks.daftar.views.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import hexageeks.daftar.R;
import hexageeks.daftar.models.User;
import hexageeks.daftar.utils.StorageUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ScannedDoc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_doc);

        ImageView img = findViewById(R.id.scanned_img);

        try {
            //byte[] byteArray = User.getInstance().temp;
            //Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

            //img.setImageBitmap(Bitmap.createScaledBitmap(bmp, 900,
            //        1000, false));

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("TESTE", "FILE - false");
        }

    }
}
