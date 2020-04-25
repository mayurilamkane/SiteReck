package be.project.sitereck.ProjectManager.POJO;



public class DataForCardProject {
    String Pname,Paddress,Pconengg,Passigdt,Pexpdt,Id;

    public DataForCardProject(String pname, String passigdt, String pexpdt) {
        Pname = pname;
        Passigdt = passigdt;
        Pexpdt = pexpdt;
    }

    public String getId() {
        return Id;
    }

    public DataForCardProject(String id, String pname, String passigdt, String pexpdt) {
        Id = id;
        Pname = pname;
        Passigdt = passigdt;
        Pexpdt = pexpdt;
    }

    public DataForCardProject(String pname, String paddress, String pconengg, String passigdt, String pexpdt) {
        Pname = pname;
        Paddress = paddress;
        Pconengg = pconengg;
        Passigdt = passigdt;
        Pexpdt = pexpdt;

    }
    public DataForCardProject(String pname, String paddress, String pconengg, String passigdt, String pexpdt, String proj_id) {
        Pname = pname;
        Paddress = paddress;
        Pconengg = pconengg;
        Passigdt = passigdt;
        Pexpdt = pexpdt;
        Id = proj_id;
    }

    public String getPname() {
        return Pname;
    }

    public String getPaddress() {
        return Paddress;
    }

    public String getPconengg() {
        return Pconengg;
    }

    public String getPassigdt() {
        return Passigdt;
    }

    public String getPexpdt() {
        return Pexpdt;
    }

    public String getProj_id() {    return Id;  }



}
