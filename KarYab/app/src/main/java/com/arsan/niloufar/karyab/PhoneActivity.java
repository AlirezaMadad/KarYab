package com.arsan.niloufar.karyab;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import ir.adad.client.Adad;

public class PhoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Adad.initialize(getApplicationContext());
        final EditText phoneNumber = (EditText) findViewById(R.id.editTextPhone);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //progressBar.setVisibility(View.VISIBLE);
                PHONE phone = new PHONE();
                phone.PHONENUMBER = Long.parseLong(phoneNumber.getText().toString().equals("") ? "0" : phoneNumber.getText().toString());
                DBHandler db = new DBHandler(PhoneActivity.this);
                IDS humanID = db.getIDbyName(IDNames.humanID);
                long ID = HttpMadad.postObjectAndGetID(phone, UrlStatic.UrlOfSetPhonenApi + "?HumanID=" + humanID.getData() + "");
                Toast.makeText(getApplicationContext(),"ثبت شد!",Toast.LENGTH_LONG).show();
                IDS.storeInDB(IDNames.phoneID, ID, PhoneActivity.this);
                //progressBar.setVisibility(View.INVISIBLE);
                //Intent intent = new Intent(getApplicationContext(), PhoneListActivity.class);
                //startActivity(intent);
                //finish();
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

}
