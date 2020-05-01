package hexageeks.daftar.models;

import java.time.LocalDateTime;
import java.util.Date;

public class StorageItem {
    private final String id;
    private final String fileName;
    private final String fileDescription;
    private final String fileUrl;
    private final String visibility;
    private final String creatorId;
    private final Date timestamp;

    public StorageItem(String id, String fileName, String fileDescription, String fileUrl,
                       String visibility, String creatorId, Date timestamp) {

        this.id = id;
        this.fileName = fileName;
        this.fileDescription = fileDescription;
        this.fileUrl = fileUrl;
        this.visibility = visibility;
        this.creatorId = creatorId;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    // TODO: Bringup file URL
    private String getFileUrl() {
        return fileUrl;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
