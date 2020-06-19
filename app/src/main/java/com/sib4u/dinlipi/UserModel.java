package com.sib4u.dinlipi;

public class UserModel {
    private String DATE;
    private String TITLE;
    private String NOTE;
    private int ID;
    private int ID2;
    private String TODO;
    int isChecked;
    private  String TIME;
    public UserModel(int ID2,String TODO,int isChecked,String TIME) {
        this.TODO = TODO;
        this.ID2=ID2;
        this.isChecked=isChecked;
        this.TIME=TIME;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public int isChecked() {
        return isChecked;
    }

    public void setChecked(int checked) {
        isChecked = checked;
    }

    public UserModel(String DATE, String TITLE, String NOTE, int ID) {
        this.DATE = DATE;
        this.TITLE = TITLE;
        this.NOTE = NOTE;
        this.ID=ID;
    }

    public int getID2() {
        return ID2;
    }

    public void setID2(int ID2) {
        this.ID2 = ID2;
    }

    public String getTODO() {
        return TODO;
    }

    public void setTODO(String TODO) {
        this.TODO = TODO;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public UserModel() {
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getNOTE() {
        return NOTE;
    }

    public void setNOTE(String NOTE) {
        this.NOTE = NOTE;
    }
}
