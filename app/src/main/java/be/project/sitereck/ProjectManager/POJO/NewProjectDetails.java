package be.project.sitereck.ProjectManager.POJO;

import java.util.List;

public class NewProjectDetails {
    public String Project_name;
    public String Project_description;
    public String Project_Start_date;
    public String Project_End_date;
    public String Project_Address;
    public String Project_latitude;
    public String Project_longitude;
    public List<Integer> Activity_id;
    public List<DataActivitiesSubActivitiesClass> Sub_Activity;

    public String getProject_name() {
        return Project_name;
    }

    public void setProject_name(String project_name) {
        Project_name = project_name;
    }

    public String getProject_description() {
        return Project_description;
    }

    public void setProject_description(String project_description) {
        Project_description = project_description;
    }

    public String getProject_Start_date() {
        return Project_Start_date;
    }

    public void setProject_Start_date(String project_Start_date) {
        Project_Start_date = project_Start_date;
    }

    public String getProject_End_date() {
        return Project_End_date;
    }

    public void setProject_End_date(String project_End_date) {
        Project_End_date = project_End_date;
    }

    public String getProject_Address() {
        return Project_Address;
    }

    public void setProject_Address(String project_Address) {
        Project_Address = project_Address;
    }

    public String getProject_latitude() {
        return Project_latitude;
    }

    public void setProject_latitude(String project_latitude) {
        Project_latitude = project_latitude;
    }

    public String getProject_longitude() {
        return Project_longitude;
    }

    public void setProject_longitude(String project_longitude) {
        Project_longitude = project_longitude;
    }

    public List<Integer> getActivity_id() {
        return Activity_id;
    }

    public void setActivity_id(List<Integer> activity_id) {
        Activity_id = activity_id;
    }

    public List<DataActivitiesSubActivitiesClass> getSub_Activity() {
        return Sub_Activity;
    }

    public void setSub_Activity(List<DataActivitiesSubActivitiesClass> sub_Activity) {
        Sub_Activity = sub_Activity;
    }
}
