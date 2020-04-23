package be.project.sitereck.Construction_Manager.DataClass;

public class Activity_dataClass_cm {
    private String p_act_id;
    private int id;
    private String title;
    private String status;
    private String startDate;
    private String endDate;
    boolean isSelected;

    public Activity_dataClass_cm(String title,String p_act_id) {

        this.title = title;
        this.p_act_id = p_act_id;
    }

    public Activity_dataClass_cm(String title, String status, String startDate, String endDate,String proj_act_id) {
        this.title = title;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.p_act_id=proj_act_id;
    }

    public Activity_dataClass_cm(String title)
    {
        this.id=id;
        this.title=title;
        this.status=status;
        this.startDate=startDate;
        this.endDate=endDate;
        this.isSelected=isSelected;
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

    public String getP_act_id() {
        return p_act_id;
    }

    public void setP_act_id(String p_act_id) {
        this.p_act_id = p_act_id;
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

    public boolean getSelected() {

        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
