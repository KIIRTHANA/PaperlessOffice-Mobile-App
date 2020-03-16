package hexageeks.daftar.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("_id")
    public Integer id;
    @SerializedName("first_name")
    public String fName;
    @SerializedName("last_name")
    public String lName;
    @SerializedName("dob")
    public String dob;
    @SerializedName("role")
    public String role;
}
