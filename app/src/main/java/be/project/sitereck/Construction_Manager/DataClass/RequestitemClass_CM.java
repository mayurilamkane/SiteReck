package be.project.sitereck.Construction_Manager.DataClass;

public class RequestitemClass_CM {
    private  String smaterial;
    private  String mdate;


    public RequestitemClass_CM(String smaterial, String mdate) {
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
