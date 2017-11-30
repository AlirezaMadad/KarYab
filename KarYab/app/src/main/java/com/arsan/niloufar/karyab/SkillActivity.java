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


/**
 * Created by Niloufar on 4/19/2016.
 */
public class SkillActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddSkill);
        final AutoCompleteTextView skillName = (AutoCompleteTextView) findViewById(R.id.skillNameAutoCompleteTextView);
        final RatingBar skilRating = (RatingBar) findViewById(R.id.skillRatingBar);
        final TextView skillDescription = (TextView) findViewById(R.id.skillDescriptionEditText);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.asd2progressBar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                SKILL skill = new SKILL();
                skill.SKILLNAME = skillName.getText().toString();
                skill.SKILLDESCRIPTION = skillDescription.getText().toString();
                skill.SKILLEFFICENYID =Byte.parseByte(String.valueOf((int) skilRating.getRating()));
                DBHandler db = new DBHandler(SkillActivity.this);
                IDS humanID = db.getIDbyName(IDNames.humanID);
                long ID = HttpMadad.postObjectAndGetID(skill,UrlStatic.UrlOfSetSkillApi+"?HumanID="+humanID.getData()+"");
                Toast.makeText(SkillActivity.this,"ثبت شد!",Toast.LENGTH_LONG).show();
                IDS.storeInDB(IDNames.skillID, ID, SkillActivity.this);
                progressBar.setVisibility(View.INVISIBLE);
                Intent returnIntent = new Intent();
                //returnIntent.putExtra("result", result);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}