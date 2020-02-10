package be.project.sitereck.Construction_Manager.SharedPref;

import android.content.Context;
import android.content.SharedPreferences;

import be.project.sitereck.Construction_Manager.DataClass.ProjectDataClass;

public class SetSharedPrefrences {

    private static final String SP_NAME ="SITERECK" ;
    Context context;

    private String proj_id="project_id";
    private String proj_name="project_name";
    private String proj_start_date="project_start_date";
    private String proj_end_date="project_end_date";


    private  static SetSharedPrefrences  mInstance;
   /* public String getVar_proj_name() {
        return proj_name;
    }*/

    /*public String getVar_proj_startdate() {
        return proj_start_date;
    }

    public String getVar_proj_enddate() {
        return proj_end_date;
    }*/

    public SetSharedPrefrences(Context context) {
        this.context=context;
    }

      public static synchronized SetSharedPrefrences getInstance(Context context){
        if(mInstance==null){
            mInstance=new SetSharedPrefrences(context);
        }
        return mInstance;
      }
    /*public void setProjname(String name){
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        sp.putString(proj_name, name);
        sp.commit();
    }*/

    public ProjectDataClass getprojectinfo(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        return  new ProjectDataClass(
                sharedPreferences.getString(proj_name,null),
                sharedPreferences.getString(proj_start_date,null),
                sharedPreferences.getString(proj_end_date,null)
        );
    }

    public int getSharedProjid() {

        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getInt(proj_id, -1);
    }

    public String getSharedProjname() {

        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(proj_name, null);
    }

    /*public  void setProjstartdate(String startdate){
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        sp.putString(proj_start_date, startdate);
        sp.commit();
    }*/
    public String getSharedProjstartdate() {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(proj_start_date, null);

    }
   /*public void setProjenddate(String enddate){
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        sp.putString(proj_end_date, enddate);
        sp.commit();
    }*/

    public String getSharedProjenddate() {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(proj_end_date, null);
    }
}