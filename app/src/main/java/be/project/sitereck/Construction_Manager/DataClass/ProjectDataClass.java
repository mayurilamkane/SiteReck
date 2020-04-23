package be.project.sitereck.Construction_Manager.DataClass;

public class ProjectDataClass {

    String Title ,StartDate,EndDate,Id,AssignBy;
    String lat,lon;
    int image;
    private String isSelected;

    public ProjectDataClass(){
        Title = Title;
        StartDate = StartDate;
        EndDate = EndDate;
        //this.Id = Id;
    }



    public ProjectDataClass(String title, String startDate, String endDate) {
        Title = title;
        StartDate = startDate;
        EndDate = endDate;
    }

    public ProjectDataClass(String proj_name, String proj_start_date, String proj_end_date, String proj_id,String user_name) {

        Title = proj_name;
        StartDate = proj_start_date;
        EndDate = proj_end_date;
        Id = proj_id;
        AssignBy=user_name;

    }
    public ProjectDataClass(String proj_name, String proj_start_date, String proj_end_date, String proj_id,String lat ,String lon) {
        Title = proj_name;
        StartDate = proj_start_date;
        EndDate = proj_end_date;
        this.Id = proj_id;
        this.lat = lat;
        this.lon = lon;
    }

    public String getId() {
        return Id;
    }

    public String getTitle() {
        return Title;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getStartDate() {
        return StartDate;
    }

    public String getEndDate() {
        return EndDate;
    }
    public String getAssignBy(){return  AssignBy;}

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }


    public void setTitle(String title) {
        Title = title;
    }
   public void setAssignBy(String assignBy){AssignBy=assignBy;}
    public void setStartDate(String startDate) {
        StartDate = startDate;
    }
    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getSelected() {
        return isSelected;
    }

    public void setSelected(String selected) {
        isSelected = selected;
    }
}
