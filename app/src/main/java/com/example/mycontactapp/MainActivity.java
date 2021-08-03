package com.example.mycontactapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Toolbar tb;

    private RecyclerView rv;
    private FloatingActionButton fab;
    private ContactRCAdapter adapter;
    private DatabaseAccess databaseAccess;
    private static final int ADD_CONTACT_REQ_CODE=1;
    private static final int EDIT_CONTACT_REQ_CODE=1;
    private static final int PERM_REQ_CODE=1;
    public static final String CONTACT_KEY="contact_key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERM_REQ_CODE);
        }
        tb=findViewById(R.id.tb);
        tb.setTitle("");
        setSupportActionBar(tb);
        rv=findViewById(R.id.rv);
        fab=findViewById(R.id.fab);
        databaseAccess=DatabaseAccess.getInstance(this);
        databaseAccess.open();

        ArrayList<Contact> contacts=databaseAccess.getAllContact();
        databaseAccess.close();

        adapter=new ContactRCAdapter(contacts, new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int contactId) {
                Intent i=new Intent(getBaseContext(), Information_Contact.class);
                i.putExtra(CONTACT_KEY,contactId);
                startActivityForResult(i,EDIT_CONTACT_REQ_CODE);

            }
        });
        rv.setAdapter(adapter);
        RecyclerView.LayoutManager lm=new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        rv.animate();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), Information_Contact.class);
                startActivityForResult(intent,ADD_CONTACT_REQ_CODE);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        SearchView searchView=(SearchView)menu.findItem(R.id.search).getActionView();
        searchView.setSubmitButtonEnabled(true);//te3red zir bahth godem text nta3 lbahth
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            //tetnafad lma l'utilisateur yadghat 3LA zia lbahth ya3ni hheta ykemel yakteb lkalma
            public boolean onQueryTextSubmit(String query) {
                databaseAccess.open();
                ArrayList<Contact>contacts=databaseAccess.getContact(query);
                databaseAccess.close();
                adapter.setContacts(contacts);
                adapter.notifyDataSetChanged();

                return false;
            }

            @Override
            //tetnafad kol ma yzid harf fi lbahth ya3ni 9bel maydghet 3la zir lbahth
            public boolean onQueryTextChange(String newText) {
                databaseAccess.open();
                ArrayList<Contact>contacts=databaseAccess.getContact(newText);
                databaseAccess.close();
                adapter.setContacts(contacts);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        //tostad3a lma ykeml l'utilisateur lbahth wycliker 3la X nsta3mloha bach n3awdo na3erdo wach kan fi la page m9bel
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                databaseAccess.open();
                ArrayList<Contact>contacts=databaseAccess.getAllContact();
                databaseAccess.close();
                adapter.setContacts(contacts);
                adapter.notifyDataSetChanged();

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADD_CONTACT_REQ_CODE ){
            databaseAccess.open();

            ArrayList<Contact>contacts=databaseAccess.getAllContact();
            databaseAccess.close();
            adapter.setContacts(contacts);
            adapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERM_REQ_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                }
                else {

                }
        }
    }
}