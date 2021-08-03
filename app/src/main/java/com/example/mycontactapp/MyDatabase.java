package com.example.mycontactapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase extends SQLiteOpenHelper {
    public static final String DB_NAME="contact.db";
     public static final int DB_VERSION=4;
    public static final String CONTACT_TB_NAME="contact";
    public static final String CONTACT_CLN_ID="id";
    public static final String CONTACT_CLN_NAME="name";
    public static final String CONTACT_CLN_PHONE="phone";

    public static final String CONTACT_CLN_IMAGE="image";







    public MyDatabase(Context context){
        super(context,DB_NAME,null,DB_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //يتم استدعاءها عند انشاء الداتا بايز
        db.execSQL("CREATE TABLE "+CONTACT_TB_NAME+" ("+CONTACT_CLN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+CONTACT_CLN_NAME+" TEXT,"+CONTACT_CLN_PHONE+" TEXT,"+CONTACT_CLN_IMAGE+" TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //يتم استدعاءها عند كل تحديث للداتا بايز اي تغيير الفراسيو الا اعلا
        db.execSQL("DROP TABLE IF EXISTS "+CONTACT_TB_NAME+" ");
        onCreate(db);

    }


}
