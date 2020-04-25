package be.project.sitereck.Construction_Manager.DataClass;

public class ProjectDataClass {

    String Title ,StartDate,EndDate,Id,AssignBy,status;
    String lat,lon;
    int image;

   // public ProjectDataClass(String string, String sharedPreferencesString, String preferencesString, String s, String string1, String sharedPreferencesString1){
     //   Title = Title;
       // StartDate = StartDate;
        //EndDate = EndDate;
        //this.Id = Id;
    //}



    public ProjectDataClass(String title, String startDate, String endDate) {
        Title = title;
        StartDate = startDate;
        EndDate = endDate;
    }

    public ProjectDataClass(String proj_name, String proj_start_date, String proj_end_date, String proj_id,String user_name,String proj_status) {

        Title = proj_name;
        StartDate = proj_start_date;
        EndDate = proj_end_date;
        this.Id = proj_id;
        AssignBy=user_name;
        status=proj_status;
    }
    //  public ProjectDataClass(String proj_name, String proj_start_date, String proj_end_date, String proj_id,String lat ,String lon) {
    //    Title = proj_name;
    //  StartDate = proj_start_date;
    //EndDate = proj_end_date;
    //this.Id = proj_id;
    //this.lat = lat;
    //this.lon = lon;
    //}

    public String getId() {
        return Id;
    }

    public String getTitle() {
        return Title;
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

    public String getStatus() {
        return status;
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

    public void setStatus(String projstatus) {
        status = projstatus;
    }
}
