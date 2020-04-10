package be.project.sitereck.ProjectManager.POJO;

public class AssignCmData {
    private String Name ,Id, Email, Contact;

    public AssignCmData(String name, String id, String email, String contact) {
        Name = name;
        Id = id;
        Email = email;
        Contact = contact;
    }

    public AssignCmData(String name, String id) {
        Name = name;Id = id;
    }

    public String getName() {
        return Name;
    }
    public String getId() {
        return Id;
    }
    public String getEmail() {  return Email;   }
    public String getContact() {    return Contact; }

    public void setName(String name) {
        Name = name;
    }
}
