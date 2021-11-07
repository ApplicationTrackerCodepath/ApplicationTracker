package com.example.applicationtracker;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

@ParseClassName("Application")
public class Application extends ParseObject {

    public static final String KEY_COMPANY_NAME = "compName";
    public static final String KEY_DATE_APPLIED = "dateApplied";
    public static final String KEY_JOB_TITLE = "jobTitle";
    public static final String KEY_NOTES = "notes";
    public static final String KEY_STATUS = "Status";
    public static final String KEY_USER = "user";

    public String getCompName(){
        return getString(KEY_COMPANY_NAME);
    }

    public void setCompName(String compName){
        put(KEY_COMPANY_NAME, compName);
    }

    public Date getDateApplied(){
        return getDate(KEY_DATE_APPLIED);
    }

    public void setDateApplied(Date dateApplied){
        put(KEY_DATE_APPLIED, dateApplied);
    }

    public String getJobTitle(){
        return getString(KEY_JOB_TITLE);
    }

    public void setJobTitle(String jobTitle){
        put(KEY_JOB_TITLE, jobTitle);
    }

    public int getNotes(){
        return getInt(KEY_NOTES);
    }

    public void setNotes(int notes){
        put(KEY_NOTES, notes);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

    public void setUser (ParseUser user){
        put(KEY_USER, user);
    }
}