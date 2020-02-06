package be.project.sitereck.Construction_Manager.DataClass;

public class SubActivityClass_CM {
    String subActivityTitle;
    String status;
    int stat;

    public SubActivityClass_CM(String title)
    {
        this.subActivityTitle=title;
    }
    public SubActivityClass_CM(String sub_act_name, String p_subactivities_status) {
        this.subActivityTitle = sub_act_name;
        this.status = p_subactivities_status;
    }

    public String getSubActivityTitle() {
        return subActivityTitle;
    }

    public String getStatus() {
        return status;
    }
}
