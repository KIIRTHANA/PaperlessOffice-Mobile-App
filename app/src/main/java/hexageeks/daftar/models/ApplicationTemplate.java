package hexageeks.daftar.models;

import java.util.Date;

public class ApplicationTemplate {
    private final String id;
    private final String formId;
    private final String name;
    private final Date timestamp;

    public ApplicationTemplate(String id, String name, String formId, Date timestamp) {
        this.id = id;
        this.formId = formId;
        this.name = name;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getFormId() {
        return formId;
    }

    public String getName() {
        return name;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
