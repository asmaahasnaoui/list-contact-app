package com.example.mycontactapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseAccess {
    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;
    private  static DatabaseAccess instance;
    private DatabaseAccess(Context context){
        this.openHelper= new MyDatabase
                (context);
    }
    public static DatabaseAccess getInstance(Context context){
        if(instance==null){
            instance=new DatabaseAccess(context);
        }
        return instance;
    }
    public void open(){
        this.database=this.openHelper.getWritableDatabase();
    }
    public void close(){
        if (this.database!=null){
            this.database.close();
        }
    }
    public boolean insertContact(Contact contact){
        //moachir data base

        ContentValues values=new ContentValues();
        values.put(MyDatabase.CONTACT_CLN_NAME,contact.getName());
        values.put(MyDatabase.CONTACT_CLN_PHONE,contact.getPhone());
        values.put(MyDatabase.CONTACT_CLN_IMAGE,contact.getImage());

        long result=database.insert(MyDatabase.CONTACT_TB_NAME,null,values);
        return result !=-1;



    }
    // ta3dil data base
    //retourne le nombre le ligne li t3adlo
    public boolean updateContact(Contact contact){
        //moachir data base

        ContentValues values=new ContentValues();
        values.put(MyDatabase.CONTACT_CLN_NAME,contact.getName());
        values.put(MyDatabase.CONTACT_CLN_PHONE,contact.getPhone());
        values.put(MyDatabase.CONTACT_CLN_IMAGE,contact.getImage());

        String args[]={contact.getId()+""};
        int result=database.update(MyDatabase.CONTACT_TB_NAME,values,"id=?",args);
        return result >0;
    }
    //retourne le nombre de ligne dans un table
    public long getContactCount(){

        return DatabaseUtils.queryNumEntries(database,MyDatabase.CONTACT_TB_NAME);
    }
    public boolean deleteContact(Contact contact){

        String args[]={String.valueOf(contact.getId())};
        int result=database.delete(MyDatabase.CONTACT_TB_NAME,"id=?",args);
        return result > 0;

    }
    //istirja3 lbayanat mina ljadwal
    public ArrayList<Contact> getAllContact(){
        ArrayList<Contact> contacts=new ArrayList<>();

        //treja3 object mn naw3 cursor
        //la valeur de cursor in first -1
        Cursor cursor=database.rawQuery("SELECT * FROM " +MyDatabase.CONTACT_TB_NAME,null);
        if(cursor !=null && cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndex(MyDatabase.CONTACT_CLN_ID));
                String name=cursor.getString(cursor.getColumnIndex(MyDatabase.CONTACT_CLN_NAME));
                String phone=cursor.getString(cursor.getColumnIndex(MyDatabase.CONTACT_CLN_PHONE));
                String image=cursor.getString(cursor.getColumnIndex(MyDatabase.CONTACT_CLN_IMAGE));

                Contact c=new Contact(id,name,phone,image);
                contacts.add(c);
            }
            while(cursor.moveToNext());
            cursor.close();
        }
        return contacts;

    }
    public Contact getContact(int contactId){


        //treja3 object mn naw3 cursor
        //la valeur de cursor in first -1
        Cursor cursor=database.rawQuery("SELECT * FROM " +MyDatabase.CONTACT_TB_NAME+" WHERE "+MyDatabase.CONTACT_CLN_ID+"=?",new String[]{String.valueOf(contactId)});
        if(cursor !=null && cursor.moveToFirst()){

                int id=cursor.getInt(cursor.getColumnIndex(MyDatabase.CONTACT_CLN_ID));
                String name=cursor.getString(cursor.getColumnIndex(MyDatabase.CONTACT_CLN_NAME));
                String phone=cursor.getString(cursor.getColumnIndex(MyDatabase.CONTACT_CLN_PHONE));
                String image=cursor.getString(cursor.getColumnIndex(MyDatabase.CONTACT_CLN_IMAGE));

                Contact c=new Contact(id,name,phone,image);



            cursor.close();
            return c;
        }
        return null;

    }
    //dalt lbahth
    public ArrayList<Contact> getContact(String modelSearch){
        ArrayList<Contact> contacts=new ArrayList<>();

        //treja3 object mn naw3 cursor
        //la valeur de cursor in first -1
        Cursor cursor=database.rawQuery("SELECT * FROM "+MyDatabase.CONTACT_TB_NAME+" WHERE "+MyDatabase.CONTACT_CLN_NAME+" LIKE ?",new String[] {modelSearch+ "%"});
        if(cursor !=null && cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndex(MyDatabase.CONTACT_CLN_ID));
                String name=cursor.getString(cursor.getColumnIndex(MyDatabase.CONTACT_CLN_NAME));
                String phone=cursor.getString(cursor.getColumnIndex(MyDatabase.CONTACT_CLN_PHONE));
                String image=cursor.getString(cursor.getColumnIndex(MyDatabase.CONTACT_CLN_IMAGE));

                Contact c=new Contact(id,name,phone,image);
                contacts.add(c);
            }
            while(cursor.moveToNext());
            cursor.close();
        }
        return contacts;

    }

}


