package hexageeks.daftar.backend;

import com.android.volley.VolleyLog;

public class ServerConfig {

    public static final String host = "https://daftar-server.herokuapp.com";

    public static void debugMode(Boolean b) {
        VolleyLog.DEBUG = b;
    }
}
