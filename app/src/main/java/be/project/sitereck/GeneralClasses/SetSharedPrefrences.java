package be.project.sitereck.GeneralClasses;

import android.content.Context;
import android.content.SharedPreferences;

import be.project.sitereck.Construction_Manager.DataClass.ProjectDataClass;
import be.project.sitereck.GeneralActivities.User;

public class SetSharedPrefrences {
    Context context;


    final String SP_NAME = "SITERECK_PM";


    static final String var_login = "isLogin";
    static final String var_User_id = "user_id";
    static final String var_User_name  = "user_name";
    static final String var_User_email = "user_email";
    static final String var_User_contact = "user_contact";
    static final String var_User_position = "user_position";

    private  static be.project.sitereck.GeneralClasses.SetSharedPrefrences mInstance;


    public static synchronized be.project.sitereck.GeneralClasses.SetSharedPrefrences getInstance(Context context){
        if(mInstance==null){
            mInstance=new be.project.sitereck.GeneralClasses.SetSharedPrefrences(context);
        }
        return mInstance;
    }

    public void clearPrefrences(){
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        sp.remove("var_login");
        sp.remove("var_User_id");
        sp.remove("var_User_name");
        sp.remove("var_User_email");
        sp.remove("var_User_contact");
        sp.remove("var_User_position");

    }
    public int getVar_login() {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getInt(var_login,-1);
    }

    public String getVar_User_id() {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return String.valueOf(sp.getInt(var_User_id,-1));
    }

    public String getVar_User_name() {
        return var_User_name;
    }

    public String getVar_User_email() {
        return var_User_email;
    }

    public String getVar_User_contact() {
        return var_User_contact;
    }

    public int getVar_User_position() {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getInt(var_User_position,-1);
    }

    public String getSP_NAME() {
        return SP_NAME;
    }

    public SetSharedPrefrences(Context context) {
        this.context = context;
    }

    public void setLogin(int STATUS){
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        sp.putInt(var_login,STATUS);
        sp.commit();

    }
    public void setUser_id(int id){
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        sp.putInt(var_User_id,id);
        sp.commit();
    }

    public void setUser_position(int position){
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        sp.putInt(var_User_position,position);
        sp.commit();
    }

    public void setUser_name(String Name){
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        sp.putString(var_User_name,Name);
        sp.commit();
    }

    public void setUser_email(String Email){
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        sp.putString(var_User_email,Email);
        sp.commit();
    }


    public void setUser_contact(String Contact){
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        sp.putString(var_User_contact,Contact);
        sp.commit();
    }


    public int getSharedUserId(){
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getInt(var_User_id,-1);
    }

    public User getprojectinfo(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        return  new User(
                sharedPreferences.getInt( var_User_id, -1),
                sharedPreferences.getInt( var_User_position,-1 ),
                sharedPreferences.getString(var_User_name,null),
                sharedPreferences.getString(var_User_email,null),
                sharedPreferences.getString(var_User_contact,null)

        );
    }


}
