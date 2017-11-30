package com.arsan.niloufar.karyab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ir.adad.client.Adad;

public class EducationListActivity extends Fragment {
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_education_list, null);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Adad.initialize(getActivity());
        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabGoToPhone);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.getEducationListProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        DBHandler db = new DBHandler(getActivity());
        IDS HumanIDS = db.getIDbyName(IDNames.humanID);
        long humanID = HumanIDS.getData();
        //List<IDS> ids = db.getAllIDSByName(IDNames.experienceID);
        List<EDUCATION> educations = HttpMadad.getObjects(EDUCATION[].class,UrlStatic.UrlOfGetHumanEducationsApi+"?humanid="+humanID);
        ScrollView scrollView = (ScrollView) view.findViewById(R.id.educationListScrollView);
        LinearLayout tempLayout = new LinearLayout(getActivity());
        tempLayout.setOrientation(LinearLayout.VERTICAL);
        //for(IDS id : ids){
        for(final EDUCATION education : educations){
            //if(id.getData() != 0){
            //EXPERIENCE experience = HttpMadad.getObject(EXPERIENCE.class,UrlStatic.UrlOfGetExperienceApi + "?ID=" + id.getData()+"&HumanID="+humanID+"");
            if(education.EDUCATIONDURATION != null && education.EDUCATIONLOCATION != null){
                //RadioButton tempRadioButton = new RadioButton(getActivity());
                //tempRadioButton.setText(education.EDUCATIONLOCATION);
                //RatingBar tempRatingBar = new RatingBar(ExperienceListActivity.this);
                TextView tempNameTextView = new TextView(getActivity());
                tempNameTextView.setText("محل تحصیل: "+education.EDUCATIONLOCATION);
                tempLayout.addView(tempNameTextView);
                TextView tempTextView = new TextView(getActivity());
                tempTextView.setText("مدت دوره: " + education.EDUCATIONDURATION + " ماه");
                TextView tempBranchTextView = new TextView(getActivity());
                tempBranchTextView.setText("رشته تحصیل: "+education.EDUCATIONBRANCH);
                TextView tempDiplomaTextView = new TextView(getActivity());
                tempDiplomaTextView.setText(" مقطع تحصیل:"+education.EDUCATIONDIPLOMA);
                //tempLayout.addView(tempRadioButton);
                tempLayout.addView(tempTextView);
                tempLayout.addView(tempBranchTextView);
                tempLayout.addView(tempDiplomaTextView );
                TextView tempfinalGradeTextView = new TextView(getActivity());
                tempfinalGradeTextView.setText("معدل نهایی: " + education.FINALGRADE + "");
                tempLayout.addView(tempfinalGradeTextView);
                Button editButton = new Button(getActivity());
                editButton.setText("ویرایش");
                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Intent myIntent = new Intent(getActivity(), EducationModifyActivity.class);
                        //myIntent.putExtra("ID", education.ID);
                        //getActivity().startActivity(myIntent);
                        Intent intent = new Intent(getActivity(),EducationModifyActivity.class);
                        intent.putExtra("ID", education.ID);
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
                        boolean finished = HttpMadad.postObjectAndGetBool(education, UrlStatic.UrlOfDeleteHumanEducationApi);
                        Toast.makeText(getActivity(), "حذف شد!", Toast.LENGTH_LONG).show();
                        //IDS.storeInDB(IDNames.skillID, ID, SkillActivity.this);
                        //progressBar.setVisibility(View.INVISIBLE);
                        //Intent intent = new Intent(getApplicationContext(), EducationListActivity.class);
                        //startActivity(intent);
                        //finish();
                        getFragmentManager().beginTransaction().detach(EducationListActivity.this).attach(EducationListActivity.this).commit();
                    }
                });
                tempLayout.addView(deleteButton);
                //tempLayout.removeView(tempTextView);
                //tempLayout.removeView(tempRadioButton);
                //tempLayout.removeView(tempRatingBar);
            }
            else{
                TextView temp2TextView = new TextView(getActivity());
                temp2TextView.setText("اطلاعات در سرور پیدا نشد!");
                tempLayout.addView(temp2TextView);
                //tempLayout.removeView(temp2TextView);
            }

        }
        //}
        //((ViewGroup)scrollView.getParent()).removeView(scrollView);
        //scrollView.removeView(tempLayout);
        scrollView.addView(tempLayout);
        //tempLayout.removeView(tempLayout);
        progressBar.setVisibility(View.INVISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                //Intent intent = new Intent(getActivity(), EDUCATIONActivity.class);
                //startActivity(intent);
                Intent intent = new Intent(getActivity(),EDUCATIONActivity.class);
                startActivityForResult(intent, 1);
                //finish();
                //progressBar.setVisibility(View.INVISIBLE);

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
