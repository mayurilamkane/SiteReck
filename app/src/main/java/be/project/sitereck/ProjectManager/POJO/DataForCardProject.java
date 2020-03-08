package be.project.sitereck.ProjectManager.POJO;



public class DataForCardProject {
    String Pname,Paddress,Pconengg,Passigdt,Pexpdt;

    public DataForCardProject(String pname, String paddress, String pconengg, String passigdt, String pexpdt) {
        Pname = pname;
        Paddress = paddress;
        Pconengg = pconengg;
        Passigdt = passigdt;
        Pexpdt = pexpdt;

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




}
