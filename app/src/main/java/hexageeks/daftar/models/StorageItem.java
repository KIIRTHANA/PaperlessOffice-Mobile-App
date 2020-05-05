package hexageeks.daftar.models;

import java.time.LocalDateTime;
import java.util.Date;

import hexageeks.daftar.utils.FileUtils;

import static hexageeks.daftar.backend.ServerConfig.host;

public class StorageItem {
    private final String id;
    private final FileUtils.FileType fileType;
    private final String fileName;
    private final String fileDescription;
    private final String visibility;
    private final String creatorId;
    private final Date timestamp;

    public StorageItem(String id, FileUtils.FileType fileType, String fileName, String fileDescription,
                       String visibility, String creatorId, Date timestamp) {

        this.id = id;
        this.fileType = fileType;
        this.fileName = fileName;
        this.fileDescription = fileDescription;
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

    public String getFileUrl() {
        return host + "/storage/" + id + "?download";
    }

    public FileUtils.FileType getFileType() { return fileType; }

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
