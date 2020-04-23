package be.project.sitereck.ProjectManager.POJO;

public class ActivityManagerClass_PM {
    private String status;
    private String Title,startDate,endDate, statusDate;
    private String ID ;

    public ActivityManagerClass_PM(String status, String title, String startDate, String endDate, String statusDate, String ID) {
        this.status = status;
        Title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusDate = statusDate;
        this.ID = ID;
    }

    public String getStatusDate() { return statusDate;  }

    public String getID() { return ID;  }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public ActivityManagerClass_PM(String title, String startDate, String endDate, String status, String id) {
        this.status = status;
        Title = title;
        this.startDate = startDate;
        this.endDate = endDate;

        ID = id;
    }

    public ActivityManagerClass_PM(String title, String startDate, String endDate) {
        Title = title;
        this.startDate = startDate;
        this.endDate = endDate;

    }
    public ActivityManagerClass_PM(){}
}
