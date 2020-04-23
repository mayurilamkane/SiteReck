package be.project.sitereck.GeneralClasses;

public class URL_STRINGS {

    private static final String DOMAIN = "https://sitereck-1.000webhostapp.com/API/";

    private static final String CALL_REGISTER = DOMAIN + "Connection/register.php";
    private static final String CALL_LOGIN = DOMAIN + "Connection/login.php";
    private static final String CALL_CMLIST = DOMAIN + "PM/getCM.php";   //all CM from user table
    private static final String CALL_CMPRO = DOMAIN + "PM/getCMPROJECT.php";
    private static final String CALL_MATREQ = DOMAIN + "PM/getALLMATREQ.php";

    private static final String CALL_RCM = DOMAIN + "PM/removeCm.php";

    private static final String CALL_PROJLIST = DOMAIN +"Project/getProjectList.php";
    private static final String CALL_PROJACTLIST = DOMAIN +"Project/getProjectActList.php";
    private static final String CALL_PROJCMLIST = DOMAIN + "Project/getProjectCm.php";
    private static final String CALL_PROJNONCMLIST = DOMAIN + "Project/getProjectNonCm.php";
    private static final String CALL_ALLACTLIST = DOMAIN +"Project/AllActivityList.php";

    private static final String CALL_ADDPROJ = DOMAIN +"Project/AddProject.php";
    private static final String CALL_ADDCMPROJ = DOMAIN +"Project/addCmProject.php";
    private static final String CALL_ADDACTPROJ = DOMAIN +"Project/addActProj.php";
    private static final String CALL_CNACTPROJ = DOMAIN +"Project/createNAddAct.php";

    private static final String CALL_DWNLDREPORT = DOMAIN +"ReportGenerator/GenerateReport1.php";

    public static String getCallDwnldreport() { return CALL_DWNLDREPORT;    }

    public static String getCallCnactproj() {   return CALL_CNACTPROJ;  }
    public static String getCallAddactproj() {  return CALL_ADDACTPROJ; }
    public static String getCallAllactlist() {  return CALL_ALLACTLIST; }
    public static String getCallAddcmproj() {   return CALL_ADDCMPROJ;  }
    public static String getCallProjnoncmlist() {   return CALL_PROJNONCMLIST;  }
    public static String getCallProjremact() {  return CALL_PROJREMACT; }
    public static String getCallProjremcm() {   return CALL_PROJREMCM;  }


    private static final String CALL_PROJREMACT = DOMAIN +"Project/RemActProject.php";
    private static final String CALL_PROJREMCM = DOMAIN +"Project/RemCmProject.php";

    public static String getCallAddproj() { return CALL_ADDPROJ;    }
    public static String getCallProjactlist() { return CALL_PROJACTLIST;    }
    public static String getCallRcm() { return CALL_RCM;    }
    public static String getCallAddcmtopro() {
        return CALL_ADDCMTOPRO;
    }
    public static String getCallProjcmlist() {  return CALL_PROJCMLIST; }
    public static String getCallProjList() {  return CALL_PROJLIST; }

    private static final String CALL_ADDCMTOPRO = DOMAIN + "PM/getADDCMTOPRO.php";
    private static final String CALL_CMNONPRO = DOMAIN + "PM/getCMNONPROJECT.php";


    final String GETALLACTIVITIESANDSUBACTIVITIES = "viewAllActivities.php";
    final String ONGOINGPROJECTS = "onGoingProjects.php";
    final String ALLPROJECTS = "getProjectList.php";
    final String ProjectConstructorNotAssigned = "getProjectsNotAssignedToConstructor.php";
    final String INSERTNEWACTIVITY ="insertNewActivity.php";
    //    public String getLOGINURL(){
//        return DOMAIN+LOGIN;
//    }
    final String getConstructors = "getAllConstructionManager.php";
    final String assignConstructor = "assignConstructorToProject.php";
    final String getGanttChartDetails= "getProgressDetails.php";
    final String insertProject ="insertNewProject.php";
    final String MaterialRequestProjectWise = "getMaterialRequestProjectWise.php";

    ////
    public static  final String CALL_ACTIVITYLIST_PM = DOMAIN +"getActivityList.php";

    public static String getCallActivitylistPm() {
        return CALL_ACTIVITYLIST_PM;
    }
    ////
    public String getMaterialRequestProjectWise() {
        return DOMAIN+MaterialRequestProjectWise;
    }

    public String getGetGanttChartDetails() {
        return DOMAIN+getGanttChartDetails;
    }

    public String getInsertProject() {
        return DOMAIN+insertProject;
    }

    public String getAssignConstructor() {
        return DOMAIN+assignConstructor;
    }

    public String getGetConstructors() {
        return DOMAIN+getConstructors;
    }

    public String getINSERTNEWACTIVITY() {
        return DOMAIN+INSERTNEWACTIVITY;
    }

    public String getProjectConstructorNotAssigned() {
        return DOMAIN+ProjectConstructorNotAssigned;
    }

    public String getGETALLACTIVITIESANDSUBACTIVITIES() {
        return DOMAIN+GETALLACTIVITIESANDSUBACTIVITIES;
    }
    public String getALLPROJECTS() {    return DOMAIN+ALLPROJECTS;  }
    public String getONGOINGPROJECTS() {
        return DOMAIN+ONGOINGPROJECTS;
    }

    public static String getCallCmpro() {   return CALL_CMPRO;  }
    public static String getCallMatreq() {  return CALL_MATREQ; }
    public static String getCallCmnonpro() {    return CALL_CMNONPRO;   }
    public static String getCallRegister() {    return CALL_REGISTER;   }
    public static String getCallLogin() {   return CALL_LOGIN;  }
    public static String getCallCmList() {
        return CALL_CMLIST;
    }
}
