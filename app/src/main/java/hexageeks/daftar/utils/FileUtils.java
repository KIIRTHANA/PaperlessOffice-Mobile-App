package hexageeks.daftar.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestHandler;

import java.io.IOException;
import androidx.annotation.Nullable;
import hexageeks.daftar.R;
import hexageeks.daftar.models.User;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FileUtils {
    public enum FileType { IMAGE, PDF, UNKNOWN}

    public static FileType parseFileType(String type) {
        switch (type) {
            case "png":
            case "jpg":
            case "jpeg":
                return FileType.IMAGE;

            case "pdf":
                return FileType.PDF;

            default:
                return FileType.UNKNOWN;
        }
    }

    public static FileType getFileType(String file) {
        String ext = getFileExtension(file);
        return parseFileType(ext);
    }

    public static String getFileExtension(String file) {
        String[] splits = file.split(".");
        return splits[splits.length-1].toLowerCase();
    }

    public static void loadImageFromUrl(Context context, ImageView imageView, String imgUrl) {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        String token = User.getInstance().token;
                        String authString = "Bearer "+token;
                        Request newRequest = chain.request().newBuilder()
                                .addHeader("Authorization", authString)
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();

        Picasso p = new Picasso.Builder(context)
            .downloader(new OkHttp3Downloader(client))
            .build();

        p.load(imgUrl).error(R.drawable.file_404).into(imageView);
    }

}
