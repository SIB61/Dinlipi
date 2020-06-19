package com.sib4u.dinlipi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DbHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "TABLE_NAME";
    private static final String TABLE2_NAME = "TABLE2_NAME";

    private static final String ID = "ID";

    private static final String DATE = "DATE";
    private static final String TITLE = "TITLE";
    private static final String NOTE = "NOTE";

    private static final String TODO = "TODO";
    private static final String CHECK= "CHECka";
    private static final String TIME= "TIME";

    private final String GET_ALL=" SELECT * FROM "+TABLE_NAME;
    private final String GET2_ALL=" SELECT * FROM "+TABLE2_NAME;
    public DbHelper(@Nullable Context context) {
        super(context,"USER_DETAIL.db",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_SQL= " CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DATE + " TEXT, " + TITLE + " TEXT, " + NOTE + " TEXT)";
        String CREATE_TABLE2_SQL= " CREATE TABLE " + TABLE2_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TODO + " TEXT, " + CHECK + " INTEGER, " + TIME + " TEXT)";
        db.execSQL(CREATE_TABLE_SQL);
        db.execSQL(CREATE_TABLE2_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db=getWritableDatabase();
        onCreate(db);
        db.close();
    }
    public boolean addNote(UserModel userModel)
    {
      SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(DATE,userModel.getDATE());
        cv.put(TITLE,userModel.getTITLE());
        cv.put(NOTE,userModel.getNOTE());
       long added= db.insert(TABLE_NAME,null,cv);
       db.close();
       return added==-1 ?false:true;
    }
    public boolean addTodo(UserModel userModel)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(TODO,userModel.getTODO());
        cv.put(CHECK,userModel.isChecked());
        cv.put(TIME,userModel.getTIME());
        long added= db.insert(TABLE2_NAME,null,cv);
        db.close();
        return added==-1 ?false:true;
    }

    public List<UserModel> getAll()
    {
        List<UserModel> returnList=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery(GET_ALL,null);
        if(cursor.moveToFirst()){
            int cID=cursor.getInt(0);
            String cDate=cursor.getString(1);
            String cTitle=cursor.getString(2);
            String cNote=cursor.getString(3);
            returnList.add(new UserModel(cDate, cTitle, cNote, cID));
            while (cursor.moveToNext())
            {
                cID=cursor.getInt(0);
                cDate=cursor.getString(1);
                cTitle=cursor.getString(2);
                cNote=cursor.getString(3);
                returnList.add(new UserModel(cDate, cTitle, cNote, cID));
            }
        }
        db.close();
        return returnList;
    }
    public List<UserModel> gotoDate(String date)
    {
        List<UserModel> returnList=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        String cDate,cTitle,cNote;
        int cID;
        Cursor cursor=db.rawQuery(GET_ALL,null);
        if(cursor.moveToFirst()){
            cDate=cursor.getString(1);
            if(cDate.equals(date)) {
                cID = cursor.getInt(0);
                cTitle = cursor.getString(2);
                cNote = cursor.getString(3);
                returnList.add(new UserModel(cDate, cTitle, cNote, cID));
            }
            while (cursor.moveToNext())
            {
                cDate=cursor.getString(1);
                if(cDate.equals(date)) {
                    cID = cursor.getInt(0);
                    cTitle = cursor.getString(2);
                    cNote = cursor.getString(3);
                    returnList.add(new UserModel(cDate, cTitle, cNote, cID));
                }
            }
        }
        db.close();
        return returnList;
    }
    public UserModel get(int position)
    {
        UserModel userModel;
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery(GET_ALL,null);
        if(cursor.moveToPosition(position))
        {
           userModel=new UserModel(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(0));
        }
        else {
            userModel = new UserModel("empty", "empty", "empty",-1);
        }
        return userModel;
    }
    public UserModel get2(int position)
    {
        UserModel userModel;
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery(GET2_ALL,null);
        if(cursor.moveToPosition(position))
        {
            userModel=new UserModel(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3));
        }
        else {
            userModel = new UserModel(-1, "empty",0,"empty");
        }
        return userModel;
    }
    public boolean updateAt(UserModel userModel)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(DATE,userModel.getDATE());
        cv.put(TITLE,userModel.getTITLE());
        cv.put(NOTE,userModel.getNOTE());
        String id=userModel.getID()+"";
        int in=db.update(TABLE_NAME,cv,"ID = ?",new String[]{id});
        db.close();
        return in>0? true:false;
    }

    public boolean updateToDoAt(UserModel userModel)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(CHECK,userModel.isChecked());
        cv.put(TODO,userModel.getTODO());
        cv.put(TIME,userModel.getTIME());
        String id=userModel.getID2()+"";
        int in=db.update(TABLE2_NAME,cv,"ID = ?",new String[]{id});
        db.close();
        return in>0? true:false;
    }

    public boolean deleteAt(int position)
    {
        String id=  get(position).getID()+"";
        SQLiteDatabase db=getWritableDatabase();
        int im=db.delete(TABLE_NAME,ID+"=?",new String[]{id});
        db.close();
        return im>0? true:false;
    }
    public List<UserModel> getTodo()
    {
        List<UserModel> returnList=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery(GET2_ALL,null);
        if(cursor.moveToFirst()){
            returnList.add(new UserModel(cursor.getInt(0),
                    cursor.getString(1),cursor.getInt(2),cursor.getString(3)));
            while (cursor.moveToNext())
            {
                returnList.add(new UserModel(cursor.getInt(0),
                        cursor.getString(1),cursor.getInt(2),cursor.getString(3)));
            }
        }
        db.close();
        return returnList;
    }


    public boolean deleteTodo()
    {
        SQLiteDatabase db=getWritableDatabase();
        int im=db.delete(TABLE2_NAME,CHECK+"=?",new String[]{"1"});
        db.close();
        return im>0? true:false;
    }
}
