package com.example.mycontactapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

public class Information_Contact extends AppCompatActivity {
    private static final int PICK_IMG_REQ_CODE = 1;
    public static final int ADD_CONTACT_RES_CODE = 2;
    public static final int EDIT_CONTACT_RES_CODE = 3;
    private Toolbar toolbar;
    private TextInputEditText et_name,et_phone;
    private ImageView img;
    private int contactId=-1;
     private DatabaseAccess dba;
     private Uri imgUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information__contact);
        toolbar=findViewById(R.id.tb);
        toolbar.setTitle(" ");

        setSupportActionBar(toolbar);
        img=findViewById(R.id.img_contact);
        et_name=findViewById(R.id.et_name);
        et_phone=findViewById(R.id.et_phone);
         dba=DatabaseAccess.getInstance(this);

        Intent intent=getIntent();
       contactId= intent.getIntExtra(MainActivity.CONTACT_KEY,-1);
       if(contactId==-1){
           //3amaliyat idafa
           Enablefield();
           clearfild();

       }
       else {
           //3amaliyat Ta3dil
           disablefield();
           dba.open();
          Contact c= dba.getContact(contactId);
           dba.close();
           if(c!=null){
               fillcontacttofields(c);

           }

       }
       img.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent in=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               startActivityForResult(in,PICK_IMG_REQ_CODE);
           }
       });



    }
    private  void fillcontacttofields(Contact c){
        if(c.getImage()!=null && !c.getImage().equals(""))
              img.setImageURI(Uri.parse(c.getImage()));
         et_name.setText(c.getName());
        et_phone.setText(c.getPhone());

    }
    private void disablefield(){
        img.setEnabled(false);
        et_name.setEnabled(false);
        et_phone.setEnabled(false);


    }
    private void Enablefield(){
        img.setEnabled(true);
        et_name.setEnabled(true);
        et_phone.setEnabled(true);
           }
    private void clearfild(){
        img.setImageURI(null);
        et_name.setText("");
        et_phone.setText("");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu,menu);
        MenuItem save=menu.findItem(R.id.save);
        MenuItem delete=menu.findItem(R.id.delet);
        MenuItem edit=menu.findItem(R.id.edit);
        if(contactId==-1){
            //3amaliyat idafa
            save.setVisible(true);
            delete.setVisible(false);
            edit.setVisible(false);
        }
        else {
            //3amaliyat Ta3dil
            save.setVisible(false);
            edit.setVisible(true);
            delete.setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String name,phone,image="";
        int dpl;
        dba.open();
        switch (item.getItemId()){
            case R.id.save:
                name=et_name.getText().toString();
                phone=et_phone.getText().toString();
                if(imgUri!=null)
                image=imgUri.toString();
                boolean res;
                Contact c=new Contact(contactId,name,phone,image);


                if(contactId==-1){
                    res= dba.insertContact(c);
                    if(res){
                        Toast.makeText(this,"contact added succefully",Toast.LENGTH_LONG).show();
                        setResult(ADD_CONTACT_RES_CODE,null);
                        finish();
                    }
                }
                else {
                    res=dba.updateContact(c);
                    if(res){
                        Toast.makeText(this,"contact modify succefully",Toast.LENGTH_LONG).show();
                        setResult(EDIT_CONTACT_RES_CODE,null);
                        finish();
                    }


                }



                return true;
            case R.id.delet:
                name=et_name.getText().toString();
                phone=et_phone.getText().toString();


                c=new Contact(contactId,name,phone,null);



                    res= dba.deleteContact(c);
                   if(res){
                   Toast.makeText(this,"contact delete succesfully",Toast.LENGTH_LONG).show();
                   setResult(EDIT_CONTACT_RES_CODE,null);
                   finish();}

                    return true;
            case R.id.edit:
                Enablefield();
                MenuItem save=toolbar.getMenu().findItem(R.id.save);
                MenuItem delete=toolbar.getMenu().findItem(R.id.delet);
                MenuItem edit=toolbar.getMenu().findItem(R.id.edit);
                delete.setVisible(false);
                edit.setVisible(false);
                save.setVisible(true);

                return true;



        }
        dba.close();
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMG_REQ_CODE && resultCode==RESULT_OK){
            if(data!=null){
                imgUri=data.getData();
                img.setImageURI(imgUri);
            }

        }
    }
}
