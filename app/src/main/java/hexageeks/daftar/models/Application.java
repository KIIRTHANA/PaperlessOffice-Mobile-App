package hexageeks.daftar.models;

import java.util.Date;

public class Application {

    private final String id;
    private final String name;
    private final String description;
    private final String message;
    private final String templateId;
    private final String creatorId;
    private final String creatorName;
    private final String workflowId;
    private final String assignedId;
    private final String assignedName;
    private final String formId;
    private final int status;
    private final int stage;
    private final int stages;
    private final Date timestamp;
    private Workflow workflow;

    public Application (String id, String name, String description, String message,
                        String templateId, String creatorId, String creatorName, String workflowId,
                        String assignedId, String assignedName, String formId, int status, int stage, int stages,
                        Date timestamp) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.message = message;
        this.templateId = templateId;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.workflowId = workflowId;
        this.assignedId = assignedId;
        this.assignedName = assignedName;
        this.formId = formId;
        this.status = status;
        this.stage = stage;
        this.stages = stages;
        this.timestamp = timestamp;
    }

    public Date getTimestamp() { return timestamp; }

    public int getStages() { return stages; }

    public int getStage() { return stage; }

    public int getStatus() { return status; }

    public String getFormId() { return formId; }

    public String getAssignedId() { return assignedId; }

    public String getAssignedName() { return assignedName; }

    public String getWorkflowId() { return workflowId; }

    public String getId() { return id; }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public String getMessage() { return message; }

    public String getTemplateId() { return templateId; }

    public String getCreatorId() { return creatorId; }

    public String getCreatorName() { return creatorName; }

    public void setWorkflow(Workflow f) { workflow = f; }

    public Workflow getWorkflow() { return workflow; }
}
