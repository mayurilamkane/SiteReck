package be.project.sitereck.ProjectManager.POJO;

public class DataForCard {
    private String Title, Desc;

    public DataForCard(String title, String desc){
        Title = title;
        Desc = desc;
    }

    public String getTitle() {
        return Title;
    }

    public String getDesc() {
        return Desc;
    }
}
