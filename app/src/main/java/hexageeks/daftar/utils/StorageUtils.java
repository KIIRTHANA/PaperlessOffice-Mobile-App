package hexageeks.daftar.utils;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestHandler;

import java.io.File;
import java.io.IOException;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import hexageeks.daftar.R;
import hexageeks.daftar.models.StorageItem;
import hexageeks.daftar.models.User;
import hexageeks.daftar.views.Dashboard;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.DOWNLOAD_SERVICE;

public class StorageUtils {
    public final static String STORAGE_DIR = "/Daftar";
    public final static String ROOT_DIR = Environment.getExternalStorageDirectory().toString();
    public enum FileType { IMAGE, PDF, UNKNOWN }

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

    public static void loadImageFromUri(Context context, ImageView imageView, Uri uri) {

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

        p.load(uri).error(R.drawable.file_404).into(imageView);
    }

    public static void downloadFileFromUrl(Context context, StorageItem item) {

        ActivityCompat.requestPermissions((Activity) context,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET },
                1);

        File storageDir = new File(ROOT_DIR + STORAGE_DIR);
        if (!storageDir.exists()) storageDir.mkdirs();

        File destFile = new File(storageDir, item.getFileName().replaceAll(" ", "_") + "." + item.getFileExtension());

        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(item.getFileUrl()))
                .setTitle("Daftar | " + item.getFileName())
                .setDescription("Downloading Document")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)// Visibility of the download Notification
                .setDestinationUri(Uri.fromFile(destFile))// Uri of the destination file
                .setRequiresCharging(false)// Set if charging is required to begin the download
                .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                .setAllowedOverRoaming(true);// Set if download is allowed on roaming network

        request.addRequestHeader("Authorization", "Bearer " + User.getInstance().token);
        request.allowScanningByMediaScanner();

        DownloadManager downloadManager= (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }
}
