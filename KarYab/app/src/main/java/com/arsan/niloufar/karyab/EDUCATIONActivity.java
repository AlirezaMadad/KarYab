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

public class EDUCATIONActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Adad.initialize(getApplicationContext());
        final AutoCompleteTextView educationLocation = (AutoCompleteTextView) findViewById(R.id.educationLocationAutoCompleteTextView);
        final AutoCompleteTextView educationBranch = (AutoCompleteTextView) findViewById(R.id.educationBranchAutoCompleteTextView);
        final EditText eductionDuration = (EditText) findViewById(R.id.educationDurationEditText);
        final AutoCompleteTextView educationDiploma = (AutoCompleteTextView) findViewById(R.id.educationDiplomaAutoCompleteTextView);
        final EditText finalGrade = (EditText) findViewById(R.id.finalGradeEditText);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //progressBar.setVisibility(View.VISIBLE);
                EDUCATION education = new EDUCATION();
                education.EDUCATIONLOCATION = educationLocation.getText().toString();
                education.EDUCATIONBRANCH = educationBranch.getText().toString();
                education.EDUCATIONDIPLOMA = educationDiploma.getText().toString();
                education.EDUCATIONDURATION = Byte.parseByte(eductionDuration.getText().toString().equals("")?"0":eductionDuration.getText().toString());
                education.FINALGRADE = Short.valueOf(finalGrade.getText().toString().equals("")?"0":finalGrade.getText().toString());
                DBHandler db = new DBHandler(EDUCATIONActivity.this);
                IDS humanID = db.getIDbyName(IDNames.humanID);
                long ID = HttpMadad.postObjectAndGetID(education,UrlStatic.UrlOfSetEducationApi+"?HumanID="+humanID.getData()+"");
                Toast.makeText(getApplicationContext(),"ثبت شد!",Toast.LENGTH_LONG).show();
                IDS.storeInDB(IDNames.educationID, ID, EDUCATIONActivity.this);
                //progressBar.setVisibility(View.INVISIBLE);
                //Intent intent = new Intent(getApplicationContext(),EducationListActivity.class);
                //startActivity(intent);
                //finish();
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

}
