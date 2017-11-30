package com.arsan.niloufar.karyab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ir.adad.client.Adad;

public class MailAdressModifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_adress_modify);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Adad.initialize(getApplicationContext());

        final EditText address = (EditText) findViewById(R.id.editTextEmailAddress);
        Intent myIntent = getIntent(); // gets the previously created intent
        final long ID = myIntent.getLongExtra("ID", 0);
        //Toast.makeText(MailAdressModifyActivity.this,""+ID+"" , Toast.LENGTH_LONG).show();// will return "FirstKeyValue"
        DBHandler db = new DBHandler(MailAdressModifyActivity.this);
        IDS HumanIDS = db.getIDbyName(IDNames.humanID);
        long humanID = HumanIDS.getData();
        MAILADRESS adress = HttpMadad.getObject(MAILADRESS.class, UrlStatic.UrlOfGetMailAdressApi + "?ID=" + ID + "&humanid=" + humanID);
        address.setText(adress.MAILADRESS1);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //progressBar.setVisibility(View.VISIBLE);
                MAILADRESS Address = new MAILADRESS();
                Address.ID = ID;
                Address.MAILADRESS1 = address.getText().toString().equals("")?"":address.getText().toString();
                DBHandler db = new DBHandler(MailAdressModifyActivity.this);
                IDS humanID = db.getIDbyName(IDNames.humanID);
                boolean Finished = HttpMadad.postObjectAndGetBool(Address, UrlStatic.UrlOfUpdateHumanMailAdressApi);
                Toast.makeText(getApplicationContext(),"ثبت شد!",Toast.LENGTH_LONG).show();
                //IDS.storeInDB(IDNames.MailAddressID, ID, MailAdressModifyActivity.this);
                //progressBar.setVisibility(View.INVISIBLE);
                //Intent intent = new Intent(getApplicationContext(), MailAddressListActivity.class);
                //startActivity(intent);
                //finish();
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

}
