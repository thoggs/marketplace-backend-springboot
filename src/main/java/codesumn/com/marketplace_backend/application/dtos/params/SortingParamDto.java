package codesumn.com.marketplace_backend.application.dtos.params;

public class SortingParamDto {
    private String id;
    private boolean desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDesc() {
        return desc;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }
}

