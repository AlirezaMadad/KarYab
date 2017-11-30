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

public class CellPhoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cell_phone);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Adad.initialize(getApplicationContext());
        final EditText cellPhoneNumber = (EditText) findViewById(R.id.editTextCellPhone);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //progressBar.setVisibility(View.VISIBLE);
                CELLPHONE cellphone = new CELLPHONE();
                cellphone.CELLPHONENUMBER = Long.parseLong(cellPhoneNumber.getText().toString().equals("") ? "0" : cellPhoneNumber.getText().toString());
                DBHandler db = new DBHandler(CellPhoneActivity.this);
                IDS humanID = db.getIDbyName(IDNames.humanID);
                long ID = HttpMadad.postObjectAndGetID(cellphone, UrlStatic.UrlOfSetCellPhoneApi + "?HumanID=" + humanID.getData() + "");
                Toast.makeText(CellPhoneActivity.this,"ثبت شد!",Toast.LENGTH_LONG).show();
                IDS.storeInDB(IDNames.cellPhoneID, ID, CellPhoneActivity.this);
                //progressBar.setVisibility(View.INVISIBLE);
                //Intent intent = new Intent(getApplicationContext(), CellPhoneListActivity.class);
                //startActivity(intent);
                //finish();
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

}
