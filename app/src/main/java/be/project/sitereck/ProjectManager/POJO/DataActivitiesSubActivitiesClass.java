package be.project.sitereck.ProjectManager.POJO;

public class DataActivitiesSubActivitiesClass {
    int act_id;
    int sub_act_id;
    int start_duration;
    int end_duration;
    String date;

    public DataActivitiesSubActivitiesClass(int act_id, int sub_act_id, int start_duration, int end_duration, String date) {
        this.act_id = act_id;
        this.sub_act_id = sub_act_id;
        this.start_duration = start_duration;
        this.end_duration = end_duration;
        this.date = date;
    }

    public int getAct_id() {
        return act_id;
    }

    public int getSub_act_id() {
        return sub_act_id;
    }

    public int getStart_duration() {
        return start_duration;
    }

    public int getEnd_duration() {
        return end_duration;
    }

    public String getDate() {
        return date;
    }
}
