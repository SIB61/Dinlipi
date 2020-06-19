package com.sib4u.dinlipi;

import android.content.Context;
import android.content.SharedPreferences;

public class UserDetail {
    private Context ctx;
   private SharedPreferences sharedPreferences;
    public UserDetail(Context ctx) {
        this.ctx = ctx;
       sharedPreferences=ctx.getSharedPreferences("userDetail",Context.MODE_PRIVATE);
    }
    public void setReminder(boolean b)
    {
        sharedPreferences.edit().putBoolean("reminder",b).commit();
    }
    public Boolean getReminder(){
          return sharedPreferences.getBoolean("reminder",true);
    }





    public void  save( String username , String password)
    {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("userNameKey",username);
        editor.putString("passWordKey",password);
        editor.commit();
    }
    public void changeLockStatus()
    {
        boolean returnValue;
        if(sharedPreferences.contains("lockStatus")) {
            returnValue = sharedPreferences.getBoolean("lockStatus", true);
            if(returnValue==true)
            {
                sharedPreferences.edit().putBoolean("lockStatus",false).commit();
            }
            else
            {
                sharedPreferences.edit().putBoolean("lockStatus",true).commit();
            }
        }
        else {
            sharedPreferences.edit().putBoolean("lockStatus",true).commit();
        }
    }
    public boolean lockStatus()
    {
        if(sharedPreferences.contains("lockStatus")) {
          return sharedPreferences.getBoolean("lockStatus", true);
        }
        else {
            sharedPreferences.edit().putBoolean("lockStatus",true).commit();
            return true;
        }
    }

    public String load(String key){
        String value;
        if(sharedPreferences.contains(key))
            value=sharedPreferences.getString(key,"x");
        else
            value="";
        return value;
    }

    public void SaveWritten(UserModel userModel)
    {
        sharedPreferences.edit().putString("date",userModel.getDATE())
                .putString("title",userModel.getTITLE())
                .putString("note",userModel.getNOTE()).commit();
    }
    public UserModel LoadWritten()
    {
        if(sharedPreferences.contains("date"))
        return new UserModel(sharedPreferences.getString("date","x"),sharedPreferences.getString("title","x"),
        sharedPreferences.getString("note","x"),0);
        else
            return new UserModel(" "," "," ",0);
    }
   public  void  setDarkMode(Boolean b)
   {
       sharedPreferences.edit().putBoolean("dark",b).commit();
   }
   public Boolean isDarkMood(){

            return sharedPreferences.getBoolean("dark",false);
   }







}
