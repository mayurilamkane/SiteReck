package be.project.sitereck.ProjectManager.POJO;

public class DataActivityDuration {
    String activity_name;
    String activity_id;
    String start_date;
    String end_date;
    public DataActivityDuration(String activity_name, String activity_id) {
        this.activity_name = activity_name;
        this.activity_id = activity_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public String getActivity_id() {
        return activity_id;
    }
}
