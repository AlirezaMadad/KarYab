package com.arsan.niloufar.karyab;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Niloufar on 4/17/2016.
 */
public class JobSeekerMeunActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobseekermenu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final TextView humanInfoextView = (TextView) findViewById(R.id.humanInfoTextView);
        DBHandler db = new DBHandler(JobSeekerMeunActivity.this);
        IDS id = db.getIDbyName(IDNames.humanID);
        Long ID = id.getData();
        HUMAN human = HttpMadad.getObject(HUMAN.class, UrlStatic.UrlOfGetHumanApi + "?id=" + ID + "");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabTempName);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //humanInfoextView.setText("سلام "+ Enums.Gender.ToString(human.GENDER) + " "+ human.NAME + " " + "کاربری: " + Enums.Usage.ToString(human.USAGE)+ " ");
                Intent intent = new Intent(getApplicationContext(),SkillActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
