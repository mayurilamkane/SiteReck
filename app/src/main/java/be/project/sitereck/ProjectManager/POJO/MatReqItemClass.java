package be.project.sitereck.ProjectManager.POJO;

public class MatReqItemClass {  private  String smaterial;
    private  String mdate;

    public MatReqItemClass(String smaterial, String mdate ) {
        this.smaterial=smaterial;
        this.mdate=mdate;



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