package be.project.sitereck.ProjectManager.POJO;

import java.util.HashMap;
import java.util.Map;

public class PmMiscData {

    private static HashMap<String, String> Projectlist = new HashMap<>();

    public static HashMap<String, String> getProjectlist() {
        return Projectlist;
    }

    public static void setProjectlist(HashMap<String, String> projectlist) {
        Projectlist = projectlist;
    }
    public static void clearProjectlist() {
        Projectlist.clear();
    }
}
