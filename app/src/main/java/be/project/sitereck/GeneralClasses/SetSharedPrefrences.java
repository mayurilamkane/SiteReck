package be.project.sitereck.GeneralClasses;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import be.project.sitereck.GeneralActivities.LoginActivity;
import be.project.sitereck.GeneralActivities.User;

public class SetSharedPrefrences {
    Context context;

    String SP_NAME = "SITERECK_PM";

    String var_login = "isLogin";
    String var_User_id = "user_id";
    String var_User_name  = "user_name";
    String var_User_email = "user_email";
    String var_User_contact = "user_contact";
    String var_User_position = "user_position";

    private static SetSharedPrefrences prefInstance;

    private SetSharedPrefrences(Context context){
        this.context = context;
    }
    public static  synchronized  SetSharedPrefrences getInstance(Context context){
        if( prefInstance == null){
            prefInstance = new SetSharedPrefrences(context);
        }
        return prefInstance;
    }
    /////////////////
    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(var_User_id, user.getId());
        editor.putString(var_User_name, user.getName());
        editor.putString(var_User_email, user.getEmail());
        editor.putString(var_User_contact, user.getContact());
        editor.putInt(var_User_position, user.getPosi());
        editor.putInt(var_login, 1);
        editor.apply();

    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(var_User_email, null) != null;
    }


    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(var_User_id, -1),
                sharedPreferences.getInt(var_User_position, -1),
                sharedPreferences.getString(var_User_name, null),
                sharedPreferences.getString(var_User_email, null),
                sharedPreferences.getString(var_User_contact, null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    ////////////////
    public void setLogin(int STATUS){
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE).edit();
        sp.putInt(var_login,STATUS);
        sp.commit();

    }
    public void setUser_id(int id){
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE).edit();
        sp.putInt(var_User_id,id);
        sp.commit();
    }

    public void setUser_name(String Name){
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE).edit();
        sp.putString(var_User_name,Name);
        sp.commit();
    }

    public void setUser_email(String Email){
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE).edit();
        sp.putString(var_User_email,Email);
        sp.commit();
    }


    public void setUser_contact(String Contact){
        SharedPreferences.Editor sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE).edit();
        sp.putString(var_User_contact,Contact);
        sp.commit();
    }


    public int getSharedUserId(){
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        return sp.getInt(var_User_id,-1);
    }

}
