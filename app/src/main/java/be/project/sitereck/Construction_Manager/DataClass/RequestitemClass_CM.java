package be.project.sitereck.Construction_Manager.DataClass;

public class RequestitemClass_CM {
    private  String smaterial;
    private  String mdate;
    private  String status;

    public RequestitemClass_CM(String smaterial, String mdate, String status) {
        this.smaterial=smaterial;
        this.mdate=mdate;
        this.status=status;
    }

    public  String getSmaterial() {
        return smaterial;
    }

    public  String getMdate() {
        return mdate;
    }

    public String getStatus() { return  status; }

    public void  setStatus(String status)  {   this.status=status; }

    public void setSmaterial(String smaterial) {
        this.smaterial = smaterial;
    }

    public void setMdate(String mdate) {
        this.mdate = mdate;
    }
}
