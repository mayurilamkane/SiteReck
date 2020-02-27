package be.project.sitereck.Construction_Manager.DataClass;

public class Activity_dataClass_cm {
    private int id;
    private String title;
    private String status;
    private String startDate;
    private String endDate;


    public Activity_dataClass_cm(String title, String status, String startDate, String endDate) {
        this.title = title;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public Activity_dataClass_cm(int id,String title,String status,String startDate,String endDate)
    {
        this.id=id;
        this.title=title;
        this.status=status;
        this.startDate=startDate;
        this.endDate=endDate;
    }

    public Activity_dataClass_cm(String title,String startDate,String endDate, int id)
    {
        this.id=id;
        this.title=title;
        this.startDate=startDate;
        this.endDate=endDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
