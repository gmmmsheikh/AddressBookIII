package com.college.addressbookiii.Utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.college.addressbookiii.Objects.Contact;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String TAG="DatabaseHelper";
    private static final String TABLE_NAME = "addressbook_table";

    private static final String NAME = "toDoListDatabase";
    private static final String ID = "id";
    private static final String CONTACT = "contact";
    private static final String STATUS = "status";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CONTACT + " TEXT, "
            + STATUS + " INTEGER)";

    private SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertContact(Contact contact){
        ContentValues cv = new ContentValues();
        cv.put(CONTACT, contact.getFirstName());
        cv.put(STATUS, 0);
        db.insert(TABLE_NAME, null, cv);
    }

    public ArrayList<Contact> getAllContacts(){
        ArrayList<Contact> taskList = new ArrayList<>();
        db.beginTransaction();
        Cursor cur = null;
        try{
            cur = db.query(TABLE_NAME, null, null, null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        Contact task = new Contact();
                        task.setId(cur.getInt(cur.getColumnIndex(ID)));
                        task.setFirstName(cur.getString(cur.getColumnIndex(CONTACT)));
                        task.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                        taskList.add(task);
                    }
                    while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return taskList;
    }

    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(TABLE_NAME, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updateContact(int id, String firstName) {
        ContentValues cv = new ContentValues();
        cv.put(CONTACT, firstName);
        db.update(TABLE_NAME, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void deleteContact(int id){
        db.delete(TABLE_NAME, ID + "= ?", new String[] {String.valueOf(id)});
    }
}