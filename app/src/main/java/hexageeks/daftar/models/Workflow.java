package hexageeks.daftar.models;

import java.util.Date;

public class Workflow {

    private final String id;
    private final String name;
    private final String creatorId;
    private final String creatorName;
    private final Date timestamp;
    private final int totalStages;
    private final Stage[] stages;

    public Workflow(String id, String name, String creatorId, String creatorName, Date timestamp, int totalStages, Stage[] stages) {
        this.id = id;
        this.name = name;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.timestamp = timestamp;
        this.stages = stages;
        this.totalStages = totalStages;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getStages() {
        return totalStages;
    }

    public Stage getStage(int index) {
        return stages[index];
    }

    public Stage[] getAuthorities() {
        return stages;
    }
}
