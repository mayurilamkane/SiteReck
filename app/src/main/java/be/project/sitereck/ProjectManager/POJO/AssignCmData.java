package be.project.sitereck.ProjectManager.POJO;

public class AssignCmData {
    private String Name ,Id;

    public AssignCmData(String name,String id) {
        Name = name;Id = id;
    }

    public String getName() {
        return Name;
    }
    public String getId() {
        return Id;
    }

    public void setName(String name) {
        Name = name;
    }
}
