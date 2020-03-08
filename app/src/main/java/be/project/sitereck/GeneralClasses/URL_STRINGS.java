package be.project.sitereck.GeneralClasses;

public class URL_STRINGS {

    private static final String DOMAIN = "https://sitereck-1.000webhostapp.com/API/";

    public static final String CALL_REGISTER = DOMAIN + "Connection/register.php";
    public static final String CALL_LOGIN = DOMAIN + "Connection/login.php";
    public static final String CALL_CMLIST = DOMAIN + "Connection/getCMList.php";

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
    public static String getCallRegister() {
        return CALL_REGISTER;
    }
    public static String getCallLogin() {
        return CALL_LOGIN;
    }
    public static String getCallCmList() {
        return CALL_CMLIST;
    }
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

    public String getALLPROJECTS() {
        return DOMAIN+ALLPROJECTS;
    }

    public String getONGOINGPROJECTS() {
        return DOMAIN+ONGOINGPROJECTS;
    }

}
