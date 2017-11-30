package com.arsan.niloufar.karyab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ir.adad.client.Adad;

public class AdressActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Adad.initialize(getApplicationContext());
        final EditText address = (EditText) findViewById(R.id.editTextAddress);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ADRESS Address = new ADRESS();
                Address.ADRESS1 = address.getText().toString().equals("")?"":address.getText().toString();
                DBHandler db = new DBHandler(AdressActivity.this);
                IDS humanID = db.getIDbyName(IDNames.humanID);
                long ID = HttpMadad.postObjectAndGetID(Address, UrlStatic.UrlOfSetAddressApi + "?HumanID=" + humanID.getData() + "");
                Toast.makeText(AdressActivity.this,"ثبت شد!",Toast.LENGTH_LONG).show();
                IDS.storeInDB(IDNames.AddressID, ID, AdressActivity.this);
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

    }
}
