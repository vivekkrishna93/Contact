package com.example.vivek.sharethoughts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {
    private  static  final int DATABASE_VERSION=1;
    private  static  final String  DATABASE_NAME="contacts.db";
    private  static  final String  TABLE_NAME="contacts";
    private  static  final String  COLUMN_ID="id";
    private  static  final String  COLUMN_NAME="name";
    private  static  final String  COLUMN_EMAIL="email";
    private  static  final String  DATABASE_UNAME="uname";
    private  static  final String  DATABASE_PASS="pass";
    private  static  final String  TABLE_CREATE="create table contacts (id integer primary key not null ,"+
            "name text not null,email text not null,uname text not null,pass text not null)";
    SQLiteDatabase db;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db=db;
    }
    public void insertcontact(contact c)
    {
        db=this.getWritableDatabase();
        ContentValues value=new  ContentValues();
        String query="select *from contacts";
        Cursor cur=db.rawQuery(query,null);
        int count=cur.getCount();
        value.put(COLUMN_ID,count);
        value.put(COLUMN_NAME, c.getName());
        value.put(COLUMN_EMAIL,c.getEmail());
        value.put(DATABASE_UNAME,c.getuname());
        value.put(DATABASE_PASS,c.getpass());
        db.insert(TABLE_NAME,null,value);
        db.close();
    }
    public String  searchpass(String unam)
    {
        db=this.getReadableDatabase();
        String query  ="select uname,pass from contacts";
        Cursor  cur=db.rawQuery(query,null);
        String a,b;
        b="not found";
        if(cur.moveToFirst())
        {
            do{
                a=cur.getString(0);

                if(a.equals(unam))
                {
                    b=cur.getString(1);
                    break;
                }
            }
            while (cur.moveToNext());
        }

        return b ;
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query="DROP TABLE IF EXISTS"+TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }
}
