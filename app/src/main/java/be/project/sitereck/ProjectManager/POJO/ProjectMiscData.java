package be.project.sitereck.ProjectManager.POJO;

public class ProjectMiscData {
    private static String Project_id;
    private static String Project_name;
    private static String Project_description;
    private static String Project_Start_date;
    private static String Project_End_date;
    private static String Project_Address;
    private static String Project_latitude;
    private static String Project_longitude;
    private static String Project_status;
    private static String Project_status_date;
    private static String Diff;

    public ProjectMiscData(String project_id, String project_name, String project_description, String project_Start_date, String project_End_date, String project_Address, String project_latitude, String project_longitude, String project_status) {
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
    public  ProjectMiscData(String project_id, String project_name, String project_description, String project_Start_date, String project_End_date, String project_Address, String project_latitude, String project_longitude, String project_status,String project_status_date,String diff) {
        Project_id = project_id;
        Project_name = project_name;
        Project_description = project_description;
        Project_Start_date = project_Start_date;
        Project_End_date = project_End_date;
        Project_Address = project_Address;
        Project_latitude = project_latitude;
        Project_longitude = project_longitude;
        Project_status = project_status;
        Project_status_date = project_status_date;
        Diff = diff;
    }
    public static void CreateProjectMiscData(String project_id, String project_name, String project_description, String project_Start_date, String project_End_date, String project_Address, String project_latitude, String project_longitude, String project_status,String project_status_date,String diff) {
        Project_id = project_id;
        Project_name = project_name;
        Project_description = project_description;
        Project_Start_date = project_Start_date;
        Project_End_date = project_End_date;
        Project_Address = project_Address;
        Project_latitude = project_latitude;
        Project_longitude = project_longitude;
        Project_status = project_status;
        Project_status_date = project_status_date;
        Diff = diff;
    }
    public static String getProject_status_date() {   return Project_status_date;    }

    public static String getDiff() {   return Diff;    }

    public static String getProject_id() {
        return Project_id;
    }

    public static String getProject_name() {
        return Project_name;
    }

    public static String getProject_description() {
        return Project_description;
    }

    public static String getProject_Start_date() {
        return Project_Start_date;
    }

    public static String getProject_End_date() {
        return Project_End_date;
    }

    public static String getProject_Address() {
        return Project_Address;
    }

    public static String getProject_latitude() {
        return Project_latitude;
    }

    public static String getProject_longitude() {
        return Project_longitude;
    }

    public static String getProject_status() {
        return Project_status;
    }

    public static void setProject_status(String project_status) {
        Project_status = project_status;
    }
}
