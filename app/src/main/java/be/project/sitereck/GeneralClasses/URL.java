package be.project.sitereck.GeneralClasses;

public class URL {

    private static final String Root = "http://sitereck-1.000webhostapp.com/API/";

    public static final String CALL_REGISTER = Root + "Connectoins/register.php";
    public static final String CALL_LOGIN = Root + "Connectoins/login.php";
    public static final String CALL_CMLIST = Root + "Connectoins/getCMList.php";

    public static  final String CALL_ACTIVITYLIST_PM = Root +"getActivityList.php";
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
}
