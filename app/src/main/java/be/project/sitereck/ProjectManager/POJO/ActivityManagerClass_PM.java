package be.project.sitereck.ProjectManager.POJO;

public class ActivityManagerClass_PM {
    private String status;
    private String Title,startDate,endDate;
    private String stat ;

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

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public ActivityManagerClass_PM(String status, String title, String startDate, String endDate, String stat) {
        this.status = status;
        Title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.stat = stat;
    }
    public ActivityManagerClass_PM(String title, String startDate, String endDate, String stat) {
        Title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.stat = stat;
    }
    public ActivityManagerClass_PM(String title, String startDate, String endDate) {
        Title = title;
        this.startDate = startDate;
        this.endDate = endDate;

    }
    public ActivityManagerClass_PM(){}
}
