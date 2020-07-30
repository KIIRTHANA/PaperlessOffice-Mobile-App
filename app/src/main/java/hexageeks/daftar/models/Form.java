package hexageeks.daftar.models;

public class Form {
    private final String id;
    private final String creatorId;
    private final String title;
    private final String description;

    public Form(String id, String creatorId, String title, String description) {
        this.id = id;
        this.creatorId = creatorId;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
