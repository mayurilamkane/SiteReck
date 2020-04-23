package be.project.sitereck.Construction_Manager.SharedPref;

import android.content.Context;
import android.content.SharedPreferences;

import be.project.sitereck.Construction_Manager.DataClass.ProjectDataClass;

public class SetSharedPrefrences {

   private static final String SP_NAME ="SITERECK" ;
    Context context;

    private String u_id = "u_id";
    private String proj_id="proj_id";
    private String proj_name="project_name";
    private String proj_start_date="project_start_date";
    private String proj_end_date="project_end_date";
    private String p_act_id="p_act_id";
    private String proj_status="proj_status";

    private  static SetSharedPrefrences mInstance;
    public SetSharedPrefrences(Context context) {
        this.context=context;
    }

      public static synchronized SetSharedPrefrences getInstance(Context context){
        if(mInstance==null){
            mInstance=new SetSharedPrefrences(context);
        }
        return mInstance;
      }

    public String getProj_status() {
        SharedPreferences sp = (SharedPreferences) context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        return sp.getString(proj_status,null);

    }

    public void setProj_status(String proj_status) {
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE).edit();
        sp.putString(proj_status,proj_status);
        sp.commit();
    }

    public String getU_id() {
        SharedPreferences sp = (SharedPreferences) context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        return sp.getString(u_id,null);
    }

    public String getP_act_id() {
        SharedPreferences sp = (SharedPreferences) context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        return sp.getString(p_act_id,null);
    }

    public void setP_act_id(String p_act_id) {
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE).edit();
        sp.putString(p_act_id,p_act_id);
        sp.commit();
    }

    public String getProjectId(){
        SharedPreferences sp=(SharedPreferences) context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        return sp.getString(proj_id,null);
    }
    public void setU_id(String user_id) {
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE).edit();
        sp.putString(u_id,user_id);
        sp.commit();
    }

    public void setTitle(String name ){
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        sp.putString(proj_name, name);
        sp.commit();
    }

    public void setProjId(String id){
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        sp.putString(proj_id, id);
        sp.commit();
    }

    public  void setStartDate(String startdate){
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        sp.putString(proj_start_date, startdate);
        sp.commit();
    }

    public void setEndDate(String enddate) {
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        sp.putString(proj_end_date, enddate);
        sp.commit();
    }

    public ProjectDataClass getprojectinfo(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        return  new ProjectDataClass(
                sharedPreferences.getString(proj_name,null),
                sharedPreferences.getString(proj_start_date,null),
                sharedPreferences.getString(proj_end_date,null),
                sharedPreferences.getString(proj_id,null),
                sharedPreferences.getString(u_id,null)

        );
    }


}