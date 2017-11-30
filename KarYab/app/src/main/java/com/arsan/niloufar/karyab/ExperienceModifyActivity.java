package com.arsan.niloufar.karyab;

import android.app.Activity;
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

public class ExperienceModifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_modify);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Adad.initialize(getApplicationContext());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddExperience);
        final AutoCompleteTextView experienceName = (AutoCompleteTextView) findViewById(R.id.experienceLocationNameAutoCompleteTextView);
        final EditText workDuration = (EditText) findViewById(R.id.editTextWorkDuration);
        Intent myIntent = getIntent(); // gets the previously created intent
        final long ID = myIntent.getLongExtra("ID", 0); // will return "FirstKeyValue"
        DBHandler db = new DBHandler(ExperienceModifyActivity.this);
        IDS HumanIDS = db.getIDbyName(IDNames.humanID);
        long humanID = HumanIDS.getData();
        //List<IDS> ids = db.getAllIDSByName(IDNames.skillID);
        EXPERIENCE experience = HttpMadad.getObject(EXPERIENCE.class, UrlStatic.UrlOfGetExperienceApi + "?ID=" + ID + "&humanid=" + humanID);
        experienceName.setText(experience.EXPERIENCELOCATOION);
        workDuration.setText(String.valueOf(experience.EXPERIENCEDURATION));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //progressBar.setVisibility(View.VISIBLE);
                EXPERIENCE experience = new EXPERIENCE();
                experience.ID = ID;
                experience.EXPERIENCELOCATOION = experienceName.getText().toString();
                experience.EXPERIENCEDURATION = Short.valueOf(workDuration.getText().toString().equals("") ? "0" : workDuration.getText().toString());
                DBHandler db = new DBHandler(ExperienceModifyActivity.this);
                IDS humanID = db.getIDbyName(IDNames.humanID);
                boolean finished = HttpMadad.postObjectAndGetBool(experience, UrlStatic.UrlOfUpdateHumanExperienceApi);
                Toast.makeText(getApplicationContext(),"ثبت شد!",Toast.LENGTH_LONG).show();
                //IDS.storeInDB(IDNames.experienceID, ID, ExperienceModifyActivity.this);
                //progressBar.setVisibility(View.INVISIBLE);
                //Intent intent = new Intent(getApplicationContext(), ExperienceListActivity.class);
                //startActivity(intent);
                //finish();
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

}
