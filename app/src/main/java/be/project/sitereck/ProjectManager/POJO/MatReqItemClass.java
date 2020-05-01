package be.project.sitereck.ProjectManager.POJO;

public class MatReqItemClass {
    private  String smaterialName;
    private  String mdate;
    private String RId ,PId ,RRDate,RStat,Note;

    public MatReqItemClass(String RId, String PId, String mdate, String RRDate, String smaterialName,  String RStat, String note) {
        this.smaterialName = smaterialName;
        this.mdate = mdate;
        this.RId = RId;
        this.PId = PId;
        this.RRDate = RRDate;
        this.RStat = RStat;
        Note = note;
    }

    public MatReqItemClass(String smaterial, String mdate ) {
        smaterialName=smaterial;
        this.mdate=mdate;



    }

    public String getSmaterialName() {
        return smaterialName;
    }

    public String getRId() {    return RId;     }

    public String getPId() {    return PId;     }

    public String getRRDate() { return RRDate;  }

    public String getRStat() {  return RStat;   }

    public String getNote() {   return Note;    }

    public  String getSmaterial() { return smaterialName;   }

    public  String getMdate() {
        return mdate;
    }

    public void setSmaterial(String smaterial) {
        smaterialName = smaterial;
    }

    public void setMdate(String mdate) {
        this.mdate = mdate;
    }

    public  String getAll() {
        return "id = "+RId +" pid  "+PId+" matName "+smaterialName ;
    }

}