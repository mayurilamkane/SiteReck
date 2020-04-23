package be.project.sitereck.ProjectManager;

import android.content.Context;
import android.content.SharedPreferences;

public class ActivityListPreference {
    Context context;
    final String SP_NAME = "ACT";

    static final  String var_id = "act_id";
    static final  String var_name = "act_name";
    static final  String var_list = "isempty";

    public String getSP_NAME() {    return SP_NAME; }
    public Context getContext() {   return context; }
    public void setContext(Context context) {   this.context = context; }


    public static String getVar_name() {
        return var_name;
    }

    public int getVar_list(){
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        return sp.getInt(var_list, -1);
    }

    public void setVar_id(String act_id){
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        sp.putString(var_id,act_id);
        sp.commit();
    }
    public void setVar_name(String act_Name){
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        sp.putString(var_name,act_Name);
        sp.commit();
    }
    public void setList(int STATUS){
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        sp.putInt(var_list,STATUS);
        sp.commit();

    }

}
