package be.project.sitereck.ProjectManager.POJO;

public class CMInfoDataClass {
    String CMName,email,contact,id;
    String Count ;

    public CMInfoDataClass(String CMName, String email, String contact, String id, String count) {
        this.CMName = CMName;
        this.email = email;
        this.contact = contact;
        this.id = id;
        Count = count;
    }

    public void setEmail(String email) {    this.email = email; }
    public void setCount(String count) {    Count = count; }
    public void setCMName(String CMName) {  this.CMName = CMName; }
    public void setContact(String contact) {    this.contact = contact; }
    public void setId(String id) {  this.id = id;   }

    public String getCMName() { return CMName; }
    public String getCount() {  return Count; }
    public String getId() { return id; }
    public String getContact() {    return contact;}
    public String getEmail() {  return email; }



}
