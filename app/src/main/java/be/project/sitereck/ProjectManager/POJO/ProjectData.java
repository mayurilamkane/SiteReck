package be.project.sitereck.ProjectManager.POJO;

import java.io.Serializable;

public class ProjectData  implements Serializable {

    public String Project_id;
    public String Project_name;
    public String Project_description;
    public String Project_Start_date;
    public String Project_End_date;
    public String Project_Address;
    public String Project_latitude;
    public String Project_longitude;
    public String Project_status;

    public ProjectData(String project_id, String project_name, String project_description, String project_Start_date, String project_End_date, String project_Address, String project_latitude, String project_longitude, String project_status) {
        Project_id = project_id;
        Project_name = project_name;
        Project_description = project_description;
        Project_Start_date = project_Start_date;
        Project_End_date = project_End_date;
        Project_Address = project_Address;
        Project_latitude = project_latitude;
        Project_longitude = project_longitude;
        Project_status = project_status;
    }

    public String getProject_id() {
        return Project_id;
    }

    public String getProject_name() {
        return Project_name;
    }

    public String getProject_description() {
        return Project_description;
    }

    public String getProject_Start_date() {
        return Project_Start_date;
    }

    public String getProject_End_date() {
        return Project_End_date;
    }

    public String getProject_Address() {
        return Project_Address;
    }

    public String getProject_latitude() {
        return Project_latitude;
    }

    public String getProject_longitude() {
        return Project_longitude;
    }

    public String getProject_status() {
        return Project_status;
    }

    public void setProject_status(String project_status) {
        this.Project_status = project_status;
    }
}
