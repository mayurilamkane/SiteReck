package be.project.sitereck.Construction_Manager.DataClass;

public class ProjectDataClass {
    public ProjectDataClass(String head, String desc) {
        this.head = head;
        this.desc = desc;
    }
    private String head,desc;

    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }
}
