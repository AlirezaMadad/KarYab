package com.arsan.niloufar.karyab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import ir.adad.client.Adad;


public class SkillModifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_modify);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddSkill);
        final AutoCompleteTextView skillName = (AutoCompleteTextView) findViewById(R.id.skillNameAutoCompleteTextView);
        final RatingBar skilRating = (RatingBar) findViewById(R.id.skillRatingBar);
        final TextView skillDescription = (TextView) findViewById(R.id.skillDescriptionEditText);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.asd2progressBar);
        Intent myIntent = getIntent(); // gets the previously created intent
        final long ID = myIntent.getLongExtra("ID", 0); // will return "FirstKeyValue"
        DBHandler db = new DBHandler(SkillModifyActivity.this);
        IDS HumanIDS = db.getIDbyName(IDNames.humanID);
        long humanID = HumanIDS.getData();
        //List<IDS> ids = db.getAllIDSByName(IDNames.skillID);
        SKILL skill = HttpMadad.getObject(SKILL.class, UrlStatic.UrlOfGetSkillApi + "?ID="+ID+"&humanid=" + humanID);
        skillName.setText(skill.SKILLNAME);
        skillDescription.setText(skill.SKILLDESCRIPTION);
        skilRating.setRating(skill.SKILLEFFICENYID);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                SKILL skill = new SKILL();
                skill.ID = ID;
                skill.SKILLNAME = skillName.getText().toString();
                skill.SKILLDESCRIPTION = skillDescription.getText().toString();
                skill.SKILLEFFICENYID =Byte.parseByte(String.valueOf((int) skilRating.getRating()));
                DBHandler db = new DBHandler(SkillModifyActivity.this);
                IDS humanID = db.getIDbyName(IDNames.humanID);
                boolean finished = HttpMadad.postObjectAndGetBool(skill, UrlStatic.UrlOfUpdateHumanSkillApi);
                Toast.makeText(SkillModifyActivity.this, "ثبت شد!" , Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

}
