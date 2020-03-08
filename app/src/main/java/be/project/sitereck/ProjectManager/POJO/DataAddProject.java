package be.project.sitereck.ProjectManager.POJO;

import java.util.ArrayList;
import java.util.List;


public class DataAddProject {
    String PROJECT_NAME;
    String PROJECT_DESC;
    String PROJECT_START_DATE;
    String PROJECT_END_DATE;
    String PROJECT_LATITUDE;
    String PROJECT_LONGITUDE;
    String PROJECT_ADDRESS;
   public static List<DataActivityDuration> data_activity = new ArrayList<>();
    public static List<DataActivityDuration> data_sub_activity = new ArrayList<>();

    public String getPROJECT_NAME() {
        return PROJECT_NAME;

    }


    public void setPROJECT_NAME(String PROJECT_NAME) {
        this.PROJECT_NAME = PROJECT_NAME;
    }

    public String getPROJECT_DESC() {
        return PROJECT_DESC;
    }

    public void setPROJECT_DESC(String PROJECT_DESC) {
        this.PROJECT_DESC = PROJECT_DESC;
    }

    public String getPROJECT_START_DATE() {
        return PROJECT_START_DATE;
    }

    public void setPROJECT_START_DATE(String PROJECT_START_DATE) {
        this.PROJECT_START_DATE = PROJECT_START_DATE;
    }

    public String getPROJECT_END_DATE() {
        return PROJECT_END_DATE;
    }

    public void setPROJECT_END_DATE(String PROJECT_END_DATE) {
        this.PROJECT_END_DATE = PROJECT_END_DATE;
    }

    public String getPROJECT_LATITUDE() {
        return PROJECT_LATITUDE;
    }

    public void setPROJECT_LATITUDE(String PROJECT_LATITUDE) {
        this.PROJECT_LATITUDE = PROJECT_LATITUDE;
    }

    public String getPROJECT_LONGITUDE() {
        return PROJECT_LONGITUDE;
    }

    public void setPROJECT_LONGITUDE(String PROJECT_LONGITUDE) {
        this.PROJECT_LONGITUDE = PROJECT_LONGITUDE;
    }

    public String getPROJECT_ADDRESS() {
        return PROJECT_ADDRESS;
    }

    public void setPROJECT_ADDRESS(String PROJECT_ADDRESS) {
        this.PROJECT_ADDRESS = PROJECT_ADDRESS;
    }
}
