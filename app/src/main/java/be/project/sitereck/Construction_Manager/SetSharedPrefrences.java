package be.project.sitereck.Construction_Manager;

/*import android.content.Context;
import android.content.SharedPreferences;

public class SetSharedPrefrences {

    final String SP_NAME ="SITERECK" ;
    Context context;

    public String proj_name="project_name";
    public String proj_start_date="project_start_date";
    public String proj_end_date="project_end_date";

    public String getVar_proj_name() {
        return proj_name;
    }

    public String getVar_proj_startdate() {
        return proj_start_date;
    }

    public String getVar_proj_enddate() {
        return proj_end_date;
    }

    public SetSharedPrefrences(Context context) {
        this.context=context;
    }

    public void setProjname(String name){
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        sp.putString(proj_name, name);
        sp.commit();
    }
    public String getSharedProjname() {

        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(proj_name, "");
    }

    public  void setProjstartdate(String startdate){
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        sp.putString(proj_start_date, startdate);
        sp.commit();
    }
    public String getSharedProjstartdate() {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(proj_start_date, "");

    }
    public void setProjenddate(String enddate){
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        sp.putString(proj_end_date, enddate);
        sp.commit();
    }

    public String getSharedProjenddate() {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(proj_end_date, "");
    }
}
*/