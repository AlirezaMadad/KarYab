package com.arsan.niloufar.karyab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ir.adad.client.Adad;

public class AdressModifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress_modify);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Adad.initialize(getApplicationContext());
        final EditText address = (EditText) findViewById(R.id.editTextAddress);
        Intent myIntent = getIntent(); // gets the previously created intent
        final long ID = myIntent.getLongExtra("ID", 0);
        //Toast.makeText(AdressModifyActivity.this, ""+ID, Toast.LENGTH_LONG).show();// will return "FirstKeyValue"
        DBHandler db = new DBHandler(AdressModifyActivity.this);
        IDS HumanIDS = db.getIDbyName(IDNames.humanID);
        long humanID = HumanIDS.getData();
        ADRESS adress = HttpMadad.getObject(ADRESS.class, UrlStatic.UrlOfGetAddressApi + "?ID="+ID+"&humanid=" + humanID);
        address.setText(adress.ADRESS1);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ADRESS Address = new ADRESS();
                Address.ID= ID;
                Address.ADRESS1 = address.getText().toString().equals("")?"":address.getText().toString();
                DBHandler db = new DBHandler(AdressModifyActivity.this);
                IDS humanID = db.getIDbyName(IDNames.humanID);
                boolean finished = HttpMadad.postObjectAndGetBool(Address, UrlStatic.UrlOfUpdateHumanSAddressApi);
                Toast.makeText(AdressModifyActivity.this, "ثبت شد!" , Toast.LENGTH_LONG).show();
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

}
