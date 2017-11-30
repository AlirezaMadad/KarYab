package com.arsan.niloufar.karyab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import ir.adad.client.Adad;

/**
 * Created by Niloufar on 5/2/2016.
 */
public class ExperienceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Adad.initialize(getApplicationContext());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddExperience);
        final AutoCompleteTextView experienceName = (AutoCompleteTextView) findViewById(R.id.experienceLocationNameAutoCompleteTextView);
        final EditText workDuration = (EditText) findViewById(R.id.editTextWorkDuration);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //progressBar.setVisibility(View.VISIBLE);
                EXPERIENCE experience = new EXPERIENCE();
                experience.EXPERIENCELOCATOION = experienceName.getText().toString();
                experience.EXPERIENCEDURATION = Short.valueOf(workDuration.getText().toString().equals("")?"0":workDuration.getText().toString());
                DBHandler db = new DBHandler(ExperienceActivity.this);
                IDS humanID = db.getIDbyName(IDNames.humanID);
                long ID = HttpMadad.postObjectAndGetID(experience,UrlStatic.UrlOfSetExperienceApi+"?HumanID="+humanID.getData()+"");
                Toast.makeText(getApplicationContext(),"ثبت شد!",Toast.LENGTH_LONG).show();
                IDS.storeInDB(IDNames.experienceID, ID, ExperienceActivity.this);
                //progressBar.setVisibility(View.INVISIBLE);
                //Intent intent = new Intent(getApplicationContext(),ExperienceListActivity.class);
                //startActivity(intent);
                //finish();
                Intent returnIntent = new Intent();
                //returnIntent.putExtra("result", result);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
