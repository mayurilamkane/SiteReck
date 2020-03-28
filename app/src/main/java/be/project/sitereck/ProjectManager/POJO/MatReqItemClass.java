package be.project.sitereck.ProjectManager.POJO;

public class MatReqItemClass {  private  String smaterial;
    private  String mdate;
    private  String projectName;


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public MatReqItemClass(String smaterial, String mdate, String projectName) {
        this.smaterial=smaterial;
        this.mdate=mdate;
        this.projectName = projectName;
    }

    public  String getSmaterial() {
        return smaterial;
    }

    public  String getMdate() {
        return mdate;
    }

    public void setSmaterial(String smaterial) {
        this.smaterial = smaterial;
    }

    public void setMdate(String mdate) {
        this.mdate = mdate;
    }
}