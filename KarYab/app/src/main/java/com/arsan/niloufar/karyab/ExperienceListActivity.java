package com.arsan.niloufar.karyab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import java.util.List;

import ir.adad.client.Adad;

/**
 * Created by Niloufar on 5/2/2016.
 */
public class ExperienceListActivity  extends Fragment {
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_experiencelist, null);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Adad.initialize(getActivity());
        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabGoToEducation);
        //final FloatingActionButton fabGoToExperience = (FloatingActionButton) view.findViewById(R.id.fabGoToExperience);
        //final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.getExperienceListprogressBar);
        //progressBar.setVisibility(View.VISIBLE);
        DBHandler db = new DBHandler(getActivity());
        IDS HumanIDS = db.getIDbyName(IDNames.humanID);
        long humanID = HumanIDS.getData();
        //List<IDS> ids = db.getAllIDSByName(IDNames.experienceID);
        List<EXPERIENCE> experiences = HttpMadad.getObjects(EXPERIENCE[].class,UrlStatic.UrlOfGetHumanExperiencesApi+"?humanid="+humanID);
        ScrollView scrollView = (ScrollView) view.findViewById(R.id.experienceListScrollView);
        LinearLayout tempLayout = new LinearLayout(getActivity());
        tempLayout.setOrientation(LinearLayout.VERTICAL);
        //for(IDS id : ids){
        for(final EXPERIENCE experience : experiences){
            //if(id.getData() != 0){
            //EXPERIENCE experience = HttpMadad.getObject(EXPERIENCE.class,UrlStatic.UrlOfGetExperienceApi + "?ID=" + id.getData()+"&HumanID="+humanID+"");
            //if(experience.EXPERIENCELOCATOION != null && experience.EXPERIENCEDURATION != 0){
                //RadioButton tempRadioButton = new RadioButton(getActivity());
                //tempRadioButton.setText(experience.EXPERIENCELOCATOION);
                //RatingBar tempRatingBar = new RatingBar(getActivity());
            TextView tempNameTextView = new TextView(getActivity());
            tempNameTextView.setText("محل کار: "+experience.EXPERIENCELOCATOION);
            tempLayout.addView(tempNameTextView);
                TextView tempTextView = new TextView(getActivity());
                tempTextView.setText("مدت همکاری: " + experience.EXPERIENCEDURATION + " ماه");
                //tempLayout.addView(tempRadioButton);
                tempLayout.addView(tempTextView);

            Button editButton = new Button(getActivity());
            editButton.setText("ویرایش");
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent myIntent = new Intent(getActivity(), ExperienceModifyActivity.class);
                    //myIntent.putExtra("ID", experience.ID);
                    //getActivity().startActivity(myIntent);
                    Intent intent = new Intent(getActivity(),ExperienceModifyActivity.class);
                    intent.putExtra("ID", experience.ID);
                    startActivityForResult(intent, 1);
                }
            });
            tempLayout.addView(editButton);
            Button deleteButton = new Button(getActivity());
            deleteButton.setText("پاک کردن");
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBHandler db = new DBHandler(getActivity());
                    IDS humanID = db.getIDbyName(IDNames.humanID);
                    boolean finished = HttpMadad.postObjectAndGetBool(experience, UrlStatic.UrlOfDeleteHumanExperienceeApi);
                    Toast.makeText(getActivity(), "حذف شد!", Toast.LENGTH_LONG).show();
                    //IDS.storeInDB(IDNames.skillID, ID, SkillActivity.this);
                    //progressBar.setVisibility(View.INVISIBLE);
                    //Intent intent = new Intent(getApplicationContext(), ExperienceListActivity.class);
                    //startActivity(intent);
                    //finish();
                    getFragmentManager().beginTransaction().detach(ExperienceListActivity.this).attach(ExperienceListActivity.this).commit();
                }
            });
            tempLayout.addView(deleteButton);
                //tempLayout.removeView(tempTextView);
                //tempLayout.removeView(tempRadioButton);
                //tempLayout.removeView(tempRatingBar);
            //}
            //else{
                //TextView temp2TextView = new TextView(getActivity());
                //temp2TextView.setText("اطلاعات در سرور پیدا نشد!");
                //tempLayout.addView(temp2TextView);
                //tempLayout.removeView(temp2TextView);
            //}

        }
        //}
        //((ViewGroup)scrollView.getParent()).removeView(scrollView);
        //scrollView.removeView(tempLayout);
        scrollView.addView(tempLayout);
        //tempLayout.removeView(tempLayout);
        //progressBar.setVisibility(View.INVISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // progressBar.setVisibility(View.VISIBLE);
                //Intent intent = new Intent(getActivity(), ExperienceActivity.class);
                //startActivity(intent);
                //finish();
                //progressBar.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(getActivity(),ExperienceActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                //Toast.makeText(getContext(),"fu swipe",Toast.LENGTH_LONG).show();
                getFragmentManager().beginTransaction().detach(this).attach(this).commit();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onAc

}
