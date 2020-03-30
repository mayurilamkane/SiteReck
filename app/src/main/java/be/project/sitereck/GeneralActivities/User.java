package be.project.sitereck.GeneralActivities;

public class  User {
    private int id,posi;
    private String name,password,email,contact;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosi() {
        return posi;
    }

    public void setPosi(int posi) {
        this.posi = posi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }



    public User(int id, int posi, String name, String email, String contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.posi = posi;



    }
}
